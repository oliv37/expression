package org.expression.batch.job;

import org.expression.batch.job.listener.JobCompletionNotificationListener;
import org.expression.batch.mapper.ExpressionLineMapper;
import org.expression.batch.processor.ExpressionItemProcessor;
import org.expression.model.Expression;
import org.expression.model.constant.CollectionNames;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

@Configuration
@EnableBatchProcessing
public class ImportCSVBatchConfiguration {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  // @Autowired
  // private ClearCollectionTasklet clearCollectionTasklet;

  @Autowired
  private MongoTemplate mongoTemplate;

  @Value("${expression.data.csv.filename}")
  private String csvFilename;

  @Bean
  public FlatFileItemReader<Expression> reader() {
    FlatFileItemReader<Expression> reader = new FlatFileItemReader<Expression>();
    reader.setResource(new ClassPathResource(csvFilename));
    reader.setLineMapper(new ExpressionLineMapper());
    // Skip first line header
    reader.setLinesToSkip(1);
    return reader;
  }

  @Bean
  public ExpressionItemProcessor processor() {
    return new ExpressionItemProcessor();
  }

  @Bean
  public MongoItemWriter<Expression> writer() {
    MongoItemWriter<Expression> writer = new MongoItemWriter<Expression>();
    writer.setTemplate(mongoTemplate);
    writer.setCollection(CollectionNames.EXPRESSIONS);
    return writer;
  }

  @Bean
  public Job importExpressionJob(JobCompletionNotificationListener listener) {
    return jobBuilderFactory.get("importExpressionJob").incrementer(new RunIdIncrementer())
        .listener(listener).flow(cleanCollectionStep()).next(importExpressionStep()).end().build();
  }

  @Bean
  public Step importExpressionStep() {
    return stepBuilderFactory.get("importExpressionStep").<Expression, Expression>chunk(1)
        .reader(reader()).processor(processor()).writer(writer()).build();
  }

  @Bean
  public Step cleanCollectionStep() {
    return stepBuilderFactory.get("cleanCollectionStep").tasklet((__, ___) -> {
      mongoTemplate.remove(new Query(), Expression.class);
      return RepeatStatus.FINISHED;
    }).build();
  }

}

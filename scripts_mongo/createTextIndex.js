db.expressions.createIndex( 
{
	expression: "text",
	description: "text"
},
{
	name: "ExpressionTextIndex",
	default_language: "french",
	weights: {
       expression: 2,
       description: 1
    }
}
);
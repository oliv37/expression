package org.expression.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

public abstract class AbstractAudit {

  @Version
  private Long version;
  @CreatedDate
  private DateTime createdAt;
  @LastModifiedDate
  private DateTime lastModified;

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public DateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(DateTime createdAt) {
    this.createdAt = createdAt;
  }

  public DateTime getLastModified() {
    return lastModified;
  }

  public void setLastModified(DateTime lastModified) {
    this.lastModified = lastModified;
  }

}

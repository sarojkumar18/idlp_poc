/*
 * Copyright 2024 Smarsh Inc.
 */
package com.smarsh.odpa.repository;

import com.smarsh.odpa.entity.S3AttributesItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class S3AttributesRepository {
  private static final String GC_ID = "gcid";
  private static final String KEY = "key";
  private static final String START_TIME = "startTime";
  private static final String TYPE = "type";

  @Autowired
  @Qualifier("siteMongoTemplate")
  private MongoTemplate siteMongoTemplate;

  public void upsert(S3AttributesItem s3AttributesItem) {
    try {
      Query query = new Query();
      query.addCriteria(Criteria.where(GC_ID).is(s3AttributesItem.getGcid())
          .and(KEY).is(s3AttributesItem.getKey()).and(TYPE).is(s3AttributesItem.getType()));
      Update update = new Update();
      update.max(START_TIME, s3AttributesItem.getStartTime());
      siteMongoTemplate.upsert(query, update, S3AttributesItem.class);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}
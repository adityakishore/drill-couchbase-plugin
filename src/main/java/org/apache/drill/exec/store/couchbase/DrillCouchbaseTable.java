/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.store.couchbase;

import java.util.ArrayList;

import org.apache.drill.exec.planner.logical.DrillTable;
import org.eigenbase.reltype.RelDataType;
import org.eigenbase.reltype.RelDataTypeFactory;
import org.eigenbase.sql.type.SqlTypeName;

public class DrillCouchbaseTable extends DrillTable implements DrillCouchbaseConstants {
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DrillCouchbaseTable.class);

  public DrillCouchbaseTable(String storageEngineName, CouchbaseStoragePlugin plugin, CouchbaseScanSpec scanSpec) {
    super(storageEngineName, plugin, scanSpec);
  }

  @Override
  public RelDataType getRowType(RelDataTypeFactory typeFactory) {
    ArrayList<RelDataType> typeList = new ArrayList<>();
    ArrayList<String> fieldNameList = new ArrayList<>();

    fieldNameList.add(KEY_NAME);
    typeList.add(typeFactory.createSqlType(SqlTypeName.ANY));
    fieldNameList.add(KEY_VALUE);
    typeList.add(typeFactory.createSqlType(SqlTypeName.ANY));

    return typeFactory.createStructType(typeList, fieldNameList);
  }

}

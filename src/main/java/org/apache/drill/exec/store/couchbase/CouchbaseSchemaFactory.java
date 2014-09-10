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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;

import net.hydromatic.optiq.Schema;
import net.hydromatic.optiq.SchemaPlus;
import net.hydromatic.optiq.Table;

import org.apache.drill.exec.rpc.user.UserSession;
import org.apache.drill.exec.store.AbstractSchema;
import org.apache.drill.exec.store.SchemaFactory;

import com.couchbase.client.ClusterManager;
import com.google.common.collect.ImmutableList;

public class CouchbaseSchemaFactory implements SchemaFactory {
  static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CouchbaseSchemaFactory.class);

  final String schemaName;
  final CouchbaseStoragePlugin plugin;

  public CouchbaseSchemaFactory(CouchbaseStoragePlugin plugin, String name) throws IOException {
    this.plugin = plugin;
    this.schemaName = name;
  }

  @Override
  public void registerSchemas(UserSession session, SchemaPlus parent) {
    CouchbaseSchema schema = new CouchbaseSchema(schemaName);
    SchemaPlus hPlus = parent.add(schemaName, schema);
    schema.setHolder(hPlus);
  }

  class CouchbaseSchema extends AbstractSchema {

    public CouchbaseSchema(String name) {
      super(ImmutableList.<String>of(), name);
    }

    public void setHolder(SchemaPlus plusOfThis) {
    }

    @Override
    public Schema getSubSchema(String name) {
      return null;
    }

    @Override
    public Set<String> getSubSchemaNames() {
      return Collections.emptySet();
    }

    @Override
    public Table getTable(String name) {
      CouchbaseScanSpec scanSpec = new CouchbaseScanSpec(name);
      return new DrillCouchbaseTable(schemaName, plugin, scanSpec);
    }

    @Override
    public Set<String> getTableNames() {
      try {
        ClusterManager cm = new ClusterManager(
            plugin.getConfig().getUrisAsURIs(),
            plugin.getConfig().getUsername(),
            plugin.getConfig().getPassword());
      } catch (URISyntaxException e) {
      }

      return Collections.emptySet();
    }

    @Override
    public String getTypeName() {
      return CouchbaseStoragePluginConfig.NAME;
    }

  }

}

# Server Properties

Server properties are displayed on the **Settings > Server Properties** page.

![](./images/settings-server-properties.png)

Properties are grouped into multiple section. Each property has a tooltip which describes the setting.

![](./images/tooltip.png)

## Default Values

The **Default Value** column shows the original value for a given property.

![](./images/default-value.png)

Whenever a value deviates from the default, the database displays the field highlighted in blue.

![](./images/modified-value.png)

To reset a property to the default value, leave the value blank and click **Save**.

## Changing Properties

Modifications performed on the **Server Properties** page apply instantly after clicking **Save**.

Disabled properties highlighted in grey or blue and grey cannot be changed in the user interface and must be modified in the `/opt/atsd/atsd/conf/server.properties` file. These modifications require a [database restart](./restarting.md).

![](./images/cannot-modify.png)

The `server.properties` file can also be changed on the [**Settings > Configuration Files**](./configuration-files.md) page by users with administrative permissions, however a [database restart](./restarting.md) is still required.

## Configuring Scheduled Tasks

To disable a task in the **Scheduled Tasks** table, enter **No** or use the drop-down list to select **Never** for the selected task.

![](./images/cron-drop-down.png)

To disable the `entity.group.update` or the `search.indexing.incremental` task, set the count to zero (`0`).

## Exporting Properties

Export the current properties, as well as operating system settings and metrics, in JSON format by clicking **Export Configuration** at the bottom of the page.

![](./images/export-configuration.png)

## Property Reference

**Bold** property names can only be modified in the `server.properties` file by a qualified user and require a [database restart](./restarting.md).

### HBase

Property | Default Value | Description
---|---|---
`entity.countToUseFilter` | `2` | Applies to Data API queries with entity [wildcard](../api/data/README.md#wildcards) (*). If entity count is greater than `entity.countToUseFilter`, one scan with skips is used. Otherwise a separate scan is executed for each query.
`hbase.compaction.list` | `d properties message li tag forecast` | List of HBase table names for which the major compaction is performed by the task triggered with `hbase.compaction.schedule`. The list contains ATSD data tables.
**`hbase.compression.type`** | `none` | HBase-level compression. Supported options: none, `gz`, `lzo`, `snappy`, `lz4`, and `zstd`.
**`hbase.compression.type.raw`** | `none` | HBase-level compression applied to raw time series data. Supported options are: none, `gz`, `lzo`, `snappy`, `lz4`, and `zstd`.
**`hbase.table.prefix`** | `atsd_` | Prefix added to all HBase tables created by ATSD. |
`htable.executor.corePoolSize` | `16` | Minimum number of worker threads performing HBase queries such as scans. This pool does not include series and properties write threads. |
`htable.executor.maxPoolSize` | `32` | Maximum number of worker threads performing HBase queries such as scans. This pool does not include series and properties write threads.
`last.insert.cache.max.size` | `100000` | Maximum number of series records kept in the `Last Insert` cache. If the limit is reached by the cache, records older than ten minutes, based on received time, are removed. If the cache remains more than 75% full, messages older than one minute are removed.|
`last.insert.write.period.seconds` | `15` | Interval at which updated series from the `Last Insert` cache are persisted to the database.
**`messages.timeToLive`** | `31536000` | Number of seconds after which records are deleted from the `atsd_message` table. The retention is based on the insertion time, not the record timestamp. The default is 365 days (`365*24*3600`).|
`properties.batch.size` | `512` | Number of property records sent to HBase in each `PUT` command. |
`properties.queue.limit` | `8192` | Maximum number of property records waiting in memory to be inserted into HBase. |
`properties.queue.pool.size` | `4` | Maximum number of work threads sending property records into HBase. |
`properties.queue.rejection.policy` | `BLOCK` | Policy that controls the behavior when the number of property records in memory reaches the `properties.queue.limit`. `BLOCK` policy slows down clients. `DISCARD` policy deletes most recent records in case of overflow.
`scan.caching.size` | `2048` | Number of rows sent to ATSD HBase in each RPC request. Setting the property to a low value causes unnecessary round-trips to the region server, while setting it to a large value requires more memory on the client and causes unnecessary rows to be sent to the client. |
`series.batch.size` | `1024` | Number of series samples sent to HBase in each `PUT` command. |
`series.queue.limit` | `32768` | Maximum number of series samples waiting in memory to be inserted into HBase. |
`series.queue.pool.size` | `4` | Maximum number of worker threads sending series samples into HBase.
`series.queue.rejection.policy` | `BLOCK` | Policy that controls the behavior when the number of series samples in memory reaches the `series.queue.limit`. `BLOCK` policy slows down clients, DISCARD policy deletes most recent samples in case of overflow.|

### Scheduled Tasks

Property | Default Value | Description
---|---|---
`data.compaction.schedule` | `0 0 22 * * *` | Schedule for launching a data compaction task. The task compresses raw values into a smaller number of daily rows. |
`delete.schedule` | `0 0 21 * * *` | Schedule for running a task to remove deleted entities, metrics, and properties. The task masks deleted data by placing **Delete** markers that hide the data from reading scans. |
`entity.group.update.interval` | `1 minute` | Interval at which entity groups with expression-based memberships are re-evaluated to add new entities and remove non-matching members from the group. Specify the interval in seconds, minutes, or hours.|
`expired.data.removal.schedule` | `0 0 1 * * *` | Schedule for running a task to automatically delete series samples for metrics with a configured retention interval. This task masks deleted data by placing **Delete** markers that hide the data from reading scans.|
`expired.series.removal.schedule` | `0 0 2 * * *` | Schedule for running a task to automatically delete expired time series for metrics with a configured series retention interval. This task masks deleted data by placing **Delete** markers that hide the data from reading scans. |
`hbase.compaction.schedule` | `0 0 1 * * *` | Schedule for triggering a major compaction in HBase. A major compactions removes **Delete** markers and that data is hidden. The compaction also re-writes storage files for optimal storage.|
`internal.backup.schedule` | `0 30 23 * * *` | Schedule for backing up ATSD configuration records to XML files in `./atsd/backup` directory. |
`search.indexing.full.schedule` | `0 0 3 * * *` | Schedule at which the **Series Search** index is rebuilt.|
`search.indexing.incremental.interval` | `5 minute` | Interval at which the **Series Search** index is refreshed. Specify the interval in seconds, minutes, or hours.|

### Monitoring

Property | Default Value | Description
---|---|---
**`internal.metrics.dump.path`** | `../logs/metrics.txt` | Absolute or relative path to a file containing current database monitoring parameters. Relative path must be relative to base ATSD directory. The file is refreshed every 15 seconds.
**`jmx.access.file`** | `None` | Absolute path to JMX access file containing the list of users and their read and write permissions.|
**`jmx.enabled`** | `No` | JMX service status. |
**`jmx.host`** | `localhost` | Hostname returned by the JMX service for **Data** connections.
**`jmx.password.file`** | `None` | Absolute path to JMX password file. |
**`jmx.port`** | `1099` | Port on which JMX service is listening. |

### Network

Property | Default Value | Description
---|---|---
**`http.port`** | `8088` | Port for plaintext HTTP protocol.|
**`https.keyManagerPassword`** | None | [Java SSL Key Manager](./ssl-self-signed.md) password.|
**`https.keyStorePassword`** | None | [Java SSL Key Store](./ssl-self-signed.md) password.|
**`https.port`** | `8443` | Port for secure HTTP protocol.|
**`https.trustStorePassword`** | None | [Java SSL Trust Store](./ssl-self-signed.md) password.|
`input.disconnect.on.error` | Yes | Stop the TCP connection if a malformed command is received. Applies to [Network API](../api/network/README.md) series, [property](../api/network/property.md), and [message](../api/network/message.md) commands.|
**`input.port`** | `8081` | TCP port for sending [Network API](../api/network/README.md) commands.|
**`input.socket.keep-alive`** | Yes | Enable keepalive for TCP sockets open on `input.port`|
**`input.socket.receive-buffer-size`** | -1 | Network memory buffer for processing commands received on the `input.port`.|
**`pickle.port`** | `8084` | TCP for sending commands serialized in [Python pickle format](../integration/graphite/pickle-protocol.md).|
`series.processing.pool.size`| 2 | Number of thread processing series commands received over TCP.|
**`udp.input.port`** | `8082` | UDP port for sending [Network API](../api/network/README.md) commands. |

### SQL

Property | Default Value | Description
---|---|---
`sql.metric.like.limit` | `50` | Maximum number of metrics matched by [`metric LIKE`](../sql/examples/select-atsd_series.md#metric-like-condition) expression in [`atsd_series`](../sql/examples/select-atsd_series.md#select-from-atsd_series-table) tables. |
`sql.tmp.storage.max_rows_in_memory` | `51200` | Maximum number of rows to be processed in memory by [grouping](../sql/README.md#grouping) and [ordering](../sql/README.md#ordering) queries. The limit is shared by all concurrently executing queries. If a query selects more rows than remain in shared memory, the query is processed by ATSD using a temporary table which results in increased response times.|

### Cache

Cache usage statistics are available as [`cache.size`](./monitoring.md#cache) and [`cache.used_percent`](./monitoring.md#cache) metrics, and on the **Settings > Cache Management** page.

Property | Default Value | Description
---|---|---
`cache.entity.maximum.size` | `100000` | Maximum number of entity records to keep in server memory cache.|
`cache.metric.maximum.value`| `50000` | Maximum number of metric records to keep in server memory cache.|
`cache.tagKey.maximum.size` | `50000` | Maximum number of tag names to keep in server memory cache.|
`cache.tagValue.maximum.size` | `100000` | Maximum number of tag values to keep in server memory cache.|

### Web Interface

Property | Default Value | Description
---|---|---
`entity-group.display.tags` | `None` | List of [entity group tags](../api/data/properties/examples/entity-tags-for-entitygroup.md) displayed on the Entity Group list.|
`scollector.ignore.tags` | `Environment Role` | List of tags ignored from series commands received from [scollector](../integration/scollector/README.md) agents.|

### Rule Engine

Property | Default Value | Description
---|---|---
`cancel.on.rule.change` | `No` | Trigger actions when a rule is saved or deleted.|
`system.commands.enabled` | `Yes` | Enable script execution by [Rule Engine](../rule-engine/README.md).|
`system.commands.timeout.seconds` | `15` | Interval in seconds after which the script execution ceases.|
`webdriver.chromebrowser.path` | `None` | Path to the `google-chrome` binary.|
`webdriver.chromedriver.path` | `None` | Path to the [`chromedriver`](../rule-engine/notifications/web-driver.md#option-2-chrome-driver) binary.|
`webdriver.phantomjs.path` | `None` | Path to [`phantomjs`](../rule-engine/notifications/web-driver.md#option-1-phantomjs) binary.|
`webdriver.pool.size` | `4` | Number of worker threads taking screenshots using [Web Driver](../rule-engine/notifications/web-driver.md).|
`webdriver.screenshots.enable` | `Yes` | Enable screenshot features in [Rule Engine](../rule-engine/README.md).|

### Other

Property | Default Value | Description
---|---|---
**`api.guest.access.enabled`** | `No` | Enable anonymous access to [Data API](../api/data/README.md#data-api-endpoints) and [Meta API](../api/data/README.md#meta-api-endpoints) query methods.|
`backup.data.directory` | `../backup` | Path to a directory where ATSD stores backup files.|
`default.addInsertTime` | `Yes` | Include entity/metric last insert time in the response by default.|
`delete.sleep.interval.ms` | `2000` | Millisecond interval to wait between 10-second deletion tasks.|
`delete.total.duration.minutes` | `60` | Maximum amount of time in minutes spent executing pending deletion tasks.|
`hostname`| `nurswgvml007` | Hostname of the ATSD server determined automatically by executing the `hostname` command.|
`nmon.data.directory` | `/tmp/atsd/nmon` | Absolute path to a directory where [`nmon`](../integration/nmon/README.md) agents are stored. |
`search.index.path` | `/tmp/atsd/lucene` | Absolute path to [Lucene](../rule-engine/property-search.md) index directory.|
`security.letsencrypt.renew` | `No` | Enable to check [Let's Encrypt](../administration/ssl-lets-encrypt.md) certificate once a day and attempt to renew it.|
`server.url` | `https://{atsd_hostname}:8443` | Server URL specified in email notification links, by default set to `hostname`.|
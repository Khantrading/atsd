# Graphite Format

## Patterns and `graphite.conf`

The `graphite.conf` file in the `/opt/atsd/atsd/conf/graphite.conf` directory controls how graphite commands are mapped into ATSD commands.

`graphite.conf` contains patterns used to parse incoming metrics:

`pattern =` matches incoming metrics.

`atsd-pattern =` converts a graphite metric name into an ATSD metric name, entity, and tags.

If a metric name matches the regular expression `pattern`, the metric is parsed according to `atsd-pattern`.

> Duplicate all `\` characters in `pattern`.

`graphite.conf` is parsed from top to bottom, meaning that metric names are matched to patterns in the same order they are placed in the file. Matching stops as soon as a `pattern` in satisfied.

If a metric name has more tokens than `atsd-pattern`, additional tokens are ignored.

If a metric name has less tokens than `atsd-pattern`, but still satisfies `pattern`, then the metric is parsed.
<!-- markdownlint-disable MD106 -->
If there is no `atsd-pattern` for an incoming metric name, then everything before the first period is recorded as the entity and the rest is recorded as the metric. If there are no periods in the metric name, then the default entity is set to `graphite`, and the metric name is recorded as the metric.
<!-- markdownlint-enable MD106 -->

`metric`: Metric token; multiple occurrences are combined.

`entity`: Entity token to replace the default entity (`graphite`); multiple occurrences are combined.

`tag:tag_name`: Token for the tag named `tag_name`.

`metrics`: Any number of metric tokens; can be used once per pattern.

**String constants**:

String constants in round brackets (tokens) are replaced.

String constants without round brackets (tokens) are added.

> Empty tokens are omitted.<br>
> Empty `atsd-pattern` drops all incoming metrics that satisfy `pattern`.

### Examples

```txt
INPUT: awgswgvml001.counters.axibase_com.wordpress.http.counts.api_bruteprotect_com.count
```

```txt
OUTPUT: e:awgswgvml001 m:wordpress.http.counts.count t:site=axibase_com t:url=api_bruteprotect_com
```

```txt
INPUT: servers.www-1.workers.busyWorkers
```

```txt
OUTPUT: e:www-1 m:workers.busyWorkers t:type=servers
```

```txt
INPUT: com.acmeCorp.instance01.jvm.memory.garbageCollections
```

```txt
OUTPUT: e:instance01 m:jvm.memory.garbageCollections t:type=com t:dep=acmeCorp
```

```txt
INPUT: collectd.nurdkr002.disk-sda1.disk_time.write
```

```txt
OUTPUT: e:nurdkr002 m:collectd.disk_time.write t:id=disk-sda1
```

```txt
INPUT: collectd.nurdkr002.interface-vethe538ad3.if_errors.tx
```

```txt
OUTPUT: e:nurdkr002 m:collectd.if_errors.tx t:id=interface-vethe538ad3
```

```txt
INPUT: collectd.nurdkr002.df-run-shm.percent_bytes-used
```

```txt
OUTPUT: e:nurdkr002 m:collectd.percent_bytes-used t:id=df-run-shm
```

```txt
INPUT: collectd.nurswgdkr002.df-run-shm.percent_bytes-used
```

```txt
OUTPUT: e:nurdkr002 m:collectd.percent_bytes-used t:id=run-shm
```

> Note how the token containing `df-` is used to omit that precise part the of the input.

```txt
INPUT: collectd.nurswgdkr002.df-run-shm.percent_bytes-used
```

```txt
OUTPUT: e:nurdkr002 m:collectd.df.percent_bytes-used t:id=run-shm
```

> Note how `df` is used to add it as a part of metric.

## Collectl Example

[Collectl](http://collectl.sourceforge.net/index.html) is a universal system performance monitoring tool for Linux systems. Collectl can monitor a broad set of subsystems, which currently include `buddyinfo`, `cpu`, `disk`, `inodes`, `infiniband`, `lustre`, `memory`, `network`, `nfs`, `processes`, `quadrics`, `slabs`, `sockets`, and `tcp`.

You can instrument Collectl to send data to ATSD using the Graphite format.

Review the complete Collectl documentation [here](http://collectl.sourceforge.net/Documentation.html).

Review the complete list of collected metrics [here](http://collectl.sourceforge.net/Data.html).

Download Collectl:

[http://sourceforge.net/projects/collectl/files/](http://sourceforge.net/projects/collectl/files/)

Unpack Collectl:

```sh
tar xzf collectl-4.0.2.src.tar.gz
```

Install Collectl:

```sh
cd collectl-4.0.2
```

```sh
sudo ./INSTALL
```

Run Collectl:

```sh
collectl --export graphite,atsdserver:8081
```

Verify that Collectl is running correctly:

```sh
sudo collectl --export graphite,atsdserver:8081,d=1,s=dn --rawtoo -f /var/log/collectl
```

The output contains a log of data streamed to ATSD:

```txt
nurswgvml031.disktotals.reads 0 1434010410
nurswgvml031.disktotals.readkbs 0 1434010410
nurswgvml031.disktotals.writes 40 1434010410
nurswgvml031.disktotals.writekbs 228 1434010410
nurswgvml031.nettotals.kbin 0 1434010410
nurswgvml031.nettotals.pktin 8 1434010410
nurswgvml031.nettotals.kbout 1 1434010410
nurswgvml031.nettotals.pktout 9 1434010410
nurswgvml031.disktotals.reads 0 1434010411
nurswgvml031.disktotals.readkbs 0 1434010411
nurswgvml031.disktotals.writes 8 1434010411
nurswgvml031.disktotals.writekbs 36 1434010411
```

The entity and metrics collected by Collectl is visible under the Entity and Metrics tabs in ATSD.

### Sensu Example

[Sensu](https://sensuapp.org/) is a monitoring tool written in Ruby that uses RabbitMQ as a message broker and Redis for storing data. The tool is targeted at monitoring cloud environments.

You can instrument Sensu to send data to ATSD using the Graphite format.

To setup the server and client on separate servers, refer to the [How to Configure Sensu](https://www.digitalocean.com/community/tutorials/how-to-configure-sensu-monitoring-rabbitmq-and-redis-on-ubuntu-14-04) guide.

#### Configure Sensu to send data to ATSD

To send data into ATSD, defined a TCP handler.

[Official Sensu Handler guide.](https://docs.sensu.io/sensu-core/1.8/guides/intro-to-handlers/)

```json
{
  "handlers": {
    "tcp_socket": {
      "type": "tcp",
      "socket": {
        "host": "atsdserver",
        "port": 8081
      },
      "mutator": "only_check_output"
    }
  }
}
```

Including the `"only_check_output"` `mutator` is crucial. Without it the Sensu server is going to send the entire JSON output doc into ATSD, with the metadata and not just the necessary Graphite output.

You have to find and download a check plugin (or write one yourself).
A large variety of Sensu community plugins, mainly written in Ruby, is available here: [https://github.com/sensu-plugins](https://github.com/sensu-plugins)

Next you have to create a check, for example:

```json
{
  "checks": {
    "cpu_metrics": {
      "type": "metric",
      "handlers": [
        "debug",
        "tcp_socket"
      ],
      "command": "/etc/sensu/plugins/cpu-metrics.rb --scheme stats.:::name:::.cpu",
      "subscribers": ["webservers"],
      "interval": 10
    }
  }
}
```

The `debug` handler is included for logging purposes and can be omitted.

At least one element of `subscribers` has to match an element of the `subscriptions` in your client configuration file.

`command` is basically the address of the plugin you want to execute with the option `--scheme` enabled. It allows you to preface the metric name in the plugins output. Since ATSD accepts `{prefix}.{hostname}.{check/task-name}.<...>`, you can choose `--scheme` value accordingly. If you want your Sensu client name to be included in its place, you can also insert `:::name:::` into the prefix.

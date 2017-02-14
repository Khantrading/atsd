Weekly Change Log: February 6 - February 12, 2017
=================================================

### ATSD

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3912 | sql            | Bug     | Fixed NullPointerException in queries with [JOIN](/api/sql#join) statements without a where condition                                                                                                          |
| 3910 | rule engine    | Bug     | Old rules without alias are logged with rule name                                    |
| [3909](#issue-3909) | rule engine    | Feature | Added `Discard Previous Values` filter to control processing of commands with timestamp less than latest processed one                                                                                                               |
| 3902 | api-network    | Bug     | Fixed deleting text value by [series](/api/network/series.md#series-command) commands having the [append](/api/network/series.md#text-append) flag and inserted in batch                                            |
| 3894 | rule engine    | Bug     | Fixed validation for rules using variables addressing to declared fields with type Array |
| 3893 | sql            | Bug     | Fixed delta aggregator behavior with filtered dates                                  |
| 3892 | sql            | Bug     | Fixed usage of [CASE](/api/sql#case) statement in SELECT with grouing by `time` column    |
| 3890 | sql            | Bug     | Fixed tag encoding error in high-cardinality metrics                                 |
| 3887 | UI             | Bug     | Added HTML-escaping for columns on SQL Queries page                                  | 
| 3885 | api-network    | Bug     | Fixed appending annotations in [series](/api/network/series.md#series-command) commands having the [append](/api/network/series.md#text-append) flag and inserted in batch                                             |
| 3883 | rule engine    | Bug     | Variables are allowed to be referenced by other variables                            |
| 3880 | sql            | Bug     | Fixed NullPointerException in [JOIN](/api/sql#join) query when aggregating null values   |
| [3879](#issue-3879) | rule engine    | Feature | Added time filter                                                                    |
| 3872 | sql            | Bug     | Fixed merge with [`JOIN USING entity`](/api/sql#join-with-using-entity)              |
| 3860 | api-rest       | Bug     | [series](/api/network/series.md#series-command) query now returns text value with [last=true](/api/data/series/query.md#control-filter-fields)                                                             |
| 3844 | sql            | Bug     | [ISNULL](/api/sql#join) function is allowed to be a part of expression               |
| 3508 | log_aggregator | Feature | Added ability to control event message size                                          |

### Collector

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3903 | socrata        | Bug     | Implemented filtering and trimming for tag values before saving                                      |
| [3899](#issue-3899) | socrata        | Feature | Launch automated job from dataset url                                                |
| 3877 | socrata        | Bug     | Annotations are applied with numeric metric name                                         |
| [3864](#issue-3864) | socrata        | Feature | A summary table is displayed in Test mode                                            |
| [3859](#issue-3859) | socrata        | Feature | Added a URL wizard                                                                   | 

### Charts

| Issue| Category       | Type    | Subject                                                                              |
|------|----------------|---------|--------------------------------------------------------------------------------------| 
| 3908 | table          | Bug     | Fixed rows sorting when "display: none' is used                                      |
| [3901](#issue-3901) | portal    | Support | Added ability to hide rows if no alert is raised for all metrics in the row          |
| [3792](#issue-3792) | box       | Feature | Implemented class "Metro"                                                            |

## ATSD

### Issue 3909
--------------
`Discard Previous Values` option allows to discard commands timestamped earlier than the time of the last (most recent) event in the given window.
![](Images/Figure_01.png)

### Issue 3879
--------------
![](Images/Figure_02.png)


## Collector

### Issue 3899
--------------
Example of launching socrata job can be found in [this tutorial](https://github.com/axibase/atsd-use-cases/blob/master/SocrataPython/README.md)

### Issue 3864
--------------
![](Images/Figure_03.png)

### Issue 3859
--------------
![](Images/Figure_04.png)
![](Images/Figure_05.png)

The generated configuration:
![](Images/Figure_06.png)

## Charts

### Issue 3901
--------------
[Example portal](https://apps.axibase.com/chartlab/bb65c060) where rows without alerts get hidden
![](Images/Figure_07.png)

### Issue 3792
--------------
[Example of `Metro` class](https://apps.axibase.com/chartlab/6d6ae13c/2/)
![](Images/Figure_08.png)

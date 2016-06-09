# Command: Insert

## Description

Insert [series](/api/network/series.md), [property](/api/network/property.md), and [message](/api/network/message.md) commands in Network API syntax using HTTP as a transport protocol.

Multiple commands can be submitted in one request, separated by line feed character.

Commands are processed sequentially. In case of error, the server terminates parsing and discards unprocessed commands.

## Request

### Path

```elm
/api/v1/command
```

### Method

```
POST 
```

### Headers

|**Header**|**Value**|
|:---|:---|
| Content-Type | text/plain |

### Fields

The request must contains array of commands in plain-text format, separated by line feed. Each command is subject to its syntax rules:

* [series](/api/network/series.md)
* [property](/api/network/property.md)
* [message](/api/network/message.md)

## Response

### Fields

None.

### Errors

None.

## Example

### Request

#### URI

```elm
POST https://atsd_host:8443/api/v1/command
```

#### Payload

```ls
series e:DL1866 m:speed=650 m:altitude=12300
property e:abc001 t:disk k:name=sda v:size=203459 v:fs_type=nfs
series e:DL1867 m:speed=450 m:altitude=12100
message e:server001 d:2015-03-04T12:43:20+00:00 t:subject="my subject" m:"Hello, world"
```

## Additional Examples



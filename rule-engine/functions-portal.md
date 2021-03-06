# Portal Functions

## Overview

These functions attach custom portals to outgoing webhooks. For instance, invoke the function to attach a portal for an entity other than the entity in the current rule window.

## Syntax

```csharp
addPortal(string portal)
addPortal(string portal, string entity / [string | Entity] entities)
addPortal(string portal, string entity / [string | Entity] entities, string comment)
addPortal(string portal, string entity / [string | Entity] entities, string comment, map additionalParams)
```

* `portal` (**required**): Portal name. If wildcard `*` is specified, all portals for the given entity are attached to the notification. If the portal is not found by the specified name, a case-insensitive match without non-alphanumeric characters is used, for example `tcollector - Linux` becomes `tcollectorlinux` and the function returns the first matching portal.
* `entity` or `entities`: Entities for which the portal is generated. Required if the portal type is [template](../portals/portals-overview.md#template-portals).
  * `entity`: Entity name is converted to `entity` URL parameter (`&entity=test_e`). If entity is not found by name, the entity is matched by case-insensitive label.
  * `entities`: `[string | Entity] entities` are converted to `entities` URL parameter as comma-separated list (`&entities=test_e,test_e1,test_e2`). If the element object type is `Entity`, the list consists of `entity.name` fields.
* `comment`: Chart caption. If not specified or empty, a default caption is generated as `${portalName} for ${ifEmpty(entity_label, entity)}` and can be retrieved with special placeholder `$caption`. The default comment contains links to the portal, entity and rule for [Email](email.md) notifications, [Slack](notifications/slack.md) and [Discord](notifications/discord.md) webhooks.
* `additionalParams`: Map with request parameters are passed to the template portal.

The parameters can include literal values or window [placeholders](placeholders.md) such as the `entity` or `tag` value.

If `entity` or `portal` cannot be found, the function sends `Entity {entity} not found` or `Portal {portal} not found` messages instead.

## Supported Endpoints

* [Email](email.md)
* [Telegram](notifications/telegram.md)
* [Slack](notifications/slack.md)
* [Discord](notifications/discord.md)
* [HipChat](notifications/hipchat.md)

The function returns an empty string for other configurations.

## Examples

* Regular portal

```javascript
addPortal('ATSD')
```

![](./images/functions-portal-1.png)

* Template Portal for Specific Entity

```javascript
addPortal('Linux nmon', 'nurswgvml007')
```

![](./images/functions-portal-2.png)

* Custom Caption

```javascript
addPortal('collectd', 'nurswgvml007', '$caption | <@' + tags.event.user + '>')
```

![](./images/functions-portal-3.png)

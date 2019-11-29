# Control Flow

Response actions support `@foreach` and `@if/@else` statements for iteration and branching and a special function `cancelAction()` for conditional processing.

Control statements that begin with `@` must be completed with `@end{}`.

## Branching

Use `@if/@else/@end` syntax (orb tags) for conditional processing.

`@if` and `@else` branches accept boolean conditions.

```css
@if{condition_A}
@else{condition_B}
@else{}
@end{}
```

The result of applying this template is the original text less any text contained in branches that evaluates to `false`. The processed text retains non-printable characters such as space, tabs, and line breaks within the printed branches.

The following example prints the entity tags table in ASCII format for the entity `nurswgvml007`.

```javascript
@if{entity == 'nurswgvml007'}
${addTable(entity.tags, 'ascii')}
@end{}
```

Multiple checks example:

```javascript
@if{!allow_action}
Action blocked **by calendar**.
@else{!lock(action_command, 300000)}
Action is already **locked**.
@else{}
${sendTcpMessageReply(host, port, action_command, 5)}
@end{}
```

## Iteration

Use the `@foreach` template to iterate over a collection.

```javascript
@foreach{item : collection}
@{item.field}
@end{}
```

* `collection` is the name of the iterated collection.
* `item` is any name assigned to the current element of the collection.
* Refer to items in the collection using `@{}` syntax instead of the regular placeholder `${}` syntax.

```javascript
@foreach{lnk : exLinks}
@{lnk}
@end{}
```

The result is the original text plus inserted blocks of text for each item in the collection. The produced text includes non-printable characters such as space, tabs, and line breaks inside the `@foreach` block.

The following example prints an entity link for each entity in the `servers` collection.

```javascript
@foreach{srv : servers}
* @{getEntityLink(srv)}
@end{}
```

Note that each `@` declaration is replaced with text line including line breaks. Position statements properly to avoid extra lines.

```javascript
/*
The example produces a malformed markdown table since `@foreach{m : msgs}` is replaced with empty line.
*/
| date | type | source |
|--------|------|--------|
@foreach{m : msgs}
|@{m.dt}|@{m.type}|@{m.source}|
@end{}
```

```javascript
/*
Generates a proper markdown table.
*/
| date | type | source | message |
|--------|------|--------|---------|
@foreach{m : msgs}|@{m.dt}|@{m.type}|@{m.source}| @{truncate(m.message, 128)} |
@end{}
```

## Conditional Processing

The `cancelAction()` function can be called in the notification text to cancel the action.

```javascript
@if{strategy != 'StartOrder'}
  ${cancelAction()}
@end{}
```
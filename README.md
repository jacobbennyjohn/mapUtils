# mapUtils / HashMap Utils

Utils methods to read, write and cast values in/from HashMap


Get value as from HashMap using json path :

```MapUtil.get(map, "$.sports-content.sports-metadata.sports-content-codes.sports-content-code.[*].@code-key.[3]");```

Parse or Cast values in a HashMap using Json Path :

```MapUtil.parse(map, "$.sports-content.sports-event.team.[*].team-stats.@score", Caster.Type.INTEGER);```

Set/Update values in HashMap using Json Path :

```MapUtil.get(map, "$.sports-content.sports-metadata.sports-title");```

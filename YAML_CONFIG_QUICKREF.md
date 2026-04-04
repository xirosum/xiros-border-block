# YAML Config Quick Reference

## Files Changed
- ✅ `build.gradle.kts` - Replaced FastConfig with SnakeYAML dependency
- ✅ `ConfigManager.java` - New utility for YAML loading/parsing
- ✅ `BorderBlockConfig.java` - Removed @FastConfig annotations
- ✅ `BorderBlockConfigAccess.java` - Simplified to use ConfigManager
- ✅ `XirosBorderBlock.java` - Added config bootstrap call

## Config File Location
```
config/xiros-border-block/xiros-border-block-config.yml
```

## Default Config Auto-Generation
- Config directory and file are **automatically created** if missing
- Default values are used as fallback
- No manual setup required!

## Accessing Config in Code
```java
// From anywhere in the codebase
BorderBlockConfig config = BorderBlockConfigAccess.get();

// Access properties
List<BorderBlockStage> stages = config.stages;
String defaultReward = config.defaultReward;

// Use stages
BorderBlockStage currentStage = StageUtil.getStage(borderSize);
```

## YAML Syntax Reminder
```yaml
# Comments start with #

# Top-level keys
key: value

# Lists (arrays)
items:
  - item1
  - item2

# Nested objects
person:
  name: John
  age: 30
```

## Editing the Config
1. Open `config/xiros-border-block/xiros-border-block-config.yml`
2. Edit stages, items, weights as needed
3. **Restart the game** for changes to take effect
4. Check logs for "Border Block config loaded successfully"

## Adding More Stages
```yaml
stages:
  # Existing stages...
  
  # New stage
  - stageId: 3
    startingBorderSize: 5000
    endingBorderSize: 10000
    requiredItem: minecraft:your_item
    rewardMultiplier: 4
    expansionMultiplier: 4
    lootTable:
      - rewards:
          - minecraft:item1
          - minecraft:item2
        weight: 25
```

## Troubleshooting
| Issue | Solution |
|-------|----------|
| Config not loading | Check logs for errors; verify YAML syntax |
| Using default values | Check `config/xiros-border-block-config.yml` exists |
| Stage not working | Verify border sizes don't overlap; check stage order |

## No More Dependencies!
✅ FastConfigAPI removed  
✅ Only SnakeYAML for parsing (lightweight)  
✅ Full control over config format  
✅ Simpler, more maintainable code


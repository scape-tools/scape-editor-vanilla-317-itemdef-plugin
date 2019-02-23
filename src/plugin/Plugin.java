package plugin;

import scape.editor.fs.io.RSBuffer;
import scape.editor.gui.plugin.IPlugin;
import scape.editor.gui.plugin.PluginDescriptor;
import scape.editor.gui.plugin.extension.ConfigExtension;

@PluginDescriptor(name="Vanilla 317 Item Definition Plugin", authors = "Nshusa", version = "2.0.0")
public class Plugin extends ConfigExtension implements IPlugin {

    @Override
    public String applicationIcon() {
        return "icons/icon.png";
    }

    @Override
    public String fxml() {
        return "scene.fxml";
    }

    @Override
    public String[] stylesheets() {
        return new String[]{
                "css/style.css"
        };
    }

    @Override
    public String getFileName() {
        return "obj";
    }

    @Override
    protected void decode(int currentIndex, RSBuffer buffer) {
        this.id = currentIndex;
        while(true) {
            int opcode = buffer.readUByte();
            if (opcode == 0) {
                break;
            }

            if (opcode == 1) {
                modelId = buffer.readUShort();
            } else if (opcode == 2) {
                name = buffer.readString10();
            } else if (opcode == 3) {
                description = buffer.readString10();
            } else if (opcode == 4) {
                spriteScale = buffer.readUShort();
            } else if (opcode == 5) {
                spritePitch = buffer.readUShort();
            } else if (opcode == 6) {
                spriteCameraRoll = buffer.readUShort();
            } else if (opcode == 7) {
                spriteTranslateX = buffer.readUShort();
                if (spriteTranslateX > 32767) {
                    spriteTranslateX -= 0x10000;
                }
            } else if (opcode == 8) {
                spriteTranslateY = buffer.readUShort();
                if (spriteTranslateY > 32767) {
                    spriteTranslateY -= 0x10000;
                }
            } else if (opcode == 10) {
                buffer.readUShort();
            } else if (opcode == 11) {
                stackable = true;
            } else if (opcode == 12) {
                value = buffer.readInt();
            } else if (opcode == 16) {
                members = true;
            } else if (opcode == 23) {
                primaryMaleModel = buffer.readUShort();
                maleTranslation = buffer.readByte();
            } else if (opcode == 24) {
                secondaryMaleModel = buffer.readUShort();
            } else if (opcode == 25) {
                primaryFemaleModel = buffer.readUShort();
                femaleTranslation = buffer.readByte();
            } else if (opcode == 26) {
                secondaryFemaleModel = buffer.readUShort();
            } else if (opcode >= 30 && opcode < 35) {
                if (groundActions == null) {
                    groundActions = new String[5];
                }
                groundActions[opcode - 30] = buffer.readString10();
                if (groundActions[opcode - 30].equalsIgnoreCase("hidden")) {
                    groundActions[opcode - 30] = null;
                }
            } else if (opcode >= 35 && opcode < 40) {
                if (widgetActions == null) {
                    widgetActions = new String[5];
                }
                widgetActions[opcode - 35] = buffer.readString10();
            } else if (opcode == 40) {
                int count = buffer.readUByte();
                originalColours = new int[count];
                replacementColours = new int[count];
                for (int i = 0; i < count; i++) {
                    originalColours[i] = buffer.readUShort();
                    replacementColours[i] = buffer.readUShort();
                }
            } else if (opcode == 78) {
                tertiaryMaleEquipmentModel = buffer.readUShort();
            } else if (opcode == 79) {
                tertiaryFemaleEquipmentModel = buffer.readUShort();
            } else if (opcode == 90) {
                primaryMaleHeadPiece = buffer.readUShort();
            } else if (opcode == 91) {
                primaryFemaleHeadPiece = buffer.readUShort();
            } else if (opcode == 92) {
                secondaryMaleHeadPiece = buffer.readUShort();
            } else if (opcode == 93) {
                secondaryFemaleHeadPiece = buffer.readUShort();
            } else if (opcode == 95) {
                spriteCameraYaw = buffer.readUShort();
            } else if (opcode == 97) {
                noteInfoId = buffer.readUShort();
            } else if (opcode == 98) {
                notedTemplateId = buffer.readUShort();
            } else if (opcode >= 100 && opcode < 110) {
                if (stackIds == null) {
                    stackIds = new int[10];
                    stackAmounts = new int[10];
                }

                stackIds[opcode - 100] = buffer.readUShort();
                stackAmounts[opcode - 100] = buffer.readUShort();
            } else if (opcode == 110) {
                groundScaleX = buffer.readUShort();
            } else if (opcode == 111) {
                groundScaleY = buffer.readUShort();
            } else if (opcode == 112) {
                groundScaleZ = buffer.readUShort();
            } else if (opcode == 113) {
                ambience = buffer.readByte();
            } else if (opcode == 114) {
                diffusion = buffer.readByte() * 5;
            } else if (opcode == 115) {
                team = buffer.readUByte();
            }
        }
    }

    @Override
    protected void encode(RSBuffer buffer) {

        if (modelId != 0) {
            buffer.writeByte(1);
            buffer.writeShort(modelId);
        }

        if (name != null) {
            buffer.writeByte(2);
            buffer.writeString10(name);
        }

        if (description != null) {
            buffer.writeByte(3);
            buffer.writeString10(description);
        }

        if (spriteScale != 2000) {
            buffer.writeByte(4);
            buffer.writeShort(spriteScale);
        }

        if (spritePitch != 0) {
            buffer.writeByte(5);
            buffer.writeShort(spritePitch);
        }

        if (spriteCameraRoll != 0) {
            buffer.writeByte(6);
            buffer.writeShort(spriteCameraRoll);
        }

        if (spriteTranslateX != 0) {
            buffer.writeByte(7);
            buffer.writeShort(spriteTranslateX);
        }

        if (spriteTranslateY != 0) {
            buffer.writeByte(8);
            buffer.writeShort(spriteTranslateY);
        }

        if (stackable) {
            buffer.writeByte(11);
        }

        if (value != 1) {
            buffer.writeByte(12);
            buffer.writeInt(value);
        }

        if (members) {
            buffer.writeByte(16);
        }

        if (primaryMaleModel != -1 || maleTranslation != 0) {
            buffer.writeByte(23);
            buffer.writeShort(primaryMaleModel);
            buffer.writeByte(maleTranslation);
        }

        if (secondaryMaleModel != -1) {
            buffer.writeByte(24);
            buffer.writeShort(secondaryMaleModel);
        }

        if (primaryFemaleModel != -1 || femaleTranslation != 0) {
            buffer.writeByte(25);
            buffer.writeShort(primaryFemaleModel);
            buffer.writeByte(femaleTranslation);
        }

        if (secondaryFemaleModel != -1) {
            buffer.writeByte(26);
            buffer.writeShort(secondaryFemaleModel);
        }

        if (groundActions != null) {
            for (int i = 0; i < groundActions.length; i++) {
                if (groundActions[i] == null) {
                    continue;
                }

                buffer.writeByte(30 + i);
                buffer.writeString10(groundActions[i]);
            }
        }

        if (widgetActions != null && widgetActions.length < 6) {
            for (int i = 0; i < widgetActions.length; i++) {
                if (widgetActions[i] == null) {
                    continue;
                }

                buffer.writeByte(35 + i);
                buffer.writeString10(widgetActions[i]);
            }
        }

        if (originalColours != null && replacementColours != null) {
            buffer.writeByte(40);
            buffer.writeByte(originalColours.length);
            for (int i = 0; i < originalColours.length; i++) {
                buffer.writeShort(originalColours[i]);
                buffer.writeShort(replacementColours[i]);
            }
        }

        if (tertiaryMaleEquipmentModel != -1) {
            buffer.writeByte(78);
            buffer.writeShort(tertiaryMaleEquipmentModel);
        }

        if (tertiaryFemaleEquipmentModel != -1) {
            buffer.writeByte(79);
            buffer.writeShort(tertiaryFemaleEquipmentModel);
        }

        if (primaryMaleHeadPiece != -1) {
            buffer.writeByte(90);
            buffer.writeShort(primaryMaleHeadPiece);
        }

        if (primaryFemaleHeadPiece != -1) {
            buffer.writeByte(91);
            buffer.writeShort(primaryFemaleHeadPiece);
        }

        if (secondaryMaleHeadPiece != -1) {
            buffer.writeByte(92);
            buffer.writeShort(secondaryMaleHeadPiece);
        }

        if (secondaryFemaleHeadPiece != -1) {
            buffer.writeByte(93);
            buffer.writeShort(secondaryFemaleHeadPiece);
        }

        if (spriteCameraYaw != 0) {
            buffer.writeByte(95);
            buffer.writeShort(spriteCameraYaw);
        }

        if (noteInfoId != -1) {
            buffer.writeByte(97);
            buffer.writeShort(noteInfoId);
        }

        if (notedTemplateId != -1) {
            buffer.writeByte(98);
            buffer.writeShort(notedTemplateId);
        }

        if (stackIds != null && stackAmounts != null) {
            for (int i = 0; i < stackIds.length; i++) {
                buffer.writeByte(100 + i);
                buffer.writeShort(stackIds[i]);
                buffer.writeShort(stackAmounts[i]);
            }
        }

        if (groundScaleX != 128) {
            buffer.writeByte(110);
            buffer.writeShort(groundScaleX);
        }

        if (groundScaleY != 128) {
            buffer.writeByte(111);
            buffer.writeShort(groundScaleY);
        }

        if (groundScaleZ != 128) {
            buffer.writeByte(112);
            buffer.writeShort(groundScaleZ);
        }

        if (ambience != 0) {
            buffer.writeByte(113);
            buffer.writeByte(ambience);
        }

        if (diffusion != 0) {
            buffer.writeByte(114);
            buffer.writeByte(diffusion / 5);
        }

        if (team != 0) {
            buffer.writeByte(115);
            buffer.writeByte(team);
        }

        buffer.writeByte(0);
    }

    private String description;
    private byte femaleTranslation;
    private String[] groundActions;
    private int groundScaleX = 128;
    private int groundScaleY = 128;
    private int groundScaleZ = 128;
    private int id = -1;
    private String[] widgetActions;
    private int ambience;
    private int diffusion;
    private byte maleTranslation;
    private boolean members;
    private int modelId;
    private String name;
    private int notedTemplateId = -1;
    private int noteInfoId = -1;
    private int[] originalColours;
    private int primaryFemaleModel = -1;
    private int primaryFemaleHeadPiece = -1;
    private int primaryMaleModel = -1;
    private int primaryMaleHeadPiece = -1;
    private int[] replacementColours;
    private int secondaryFemaleModel = -1;
    private int secondaryFemaleHeadPiece = -1;
    private int secondaryMaleModel = -1;
    private int secondaryMaleHeadPiece = -1;
    private int spriteCameraRoll;
    private int spriteCameraYaw;
    private int spritePitch;
    private int spriteScale = 2000;
    private int spriteTranslateX;
    private int spriteTranslateY;
    private boolean stackable;
    private int[] stackAmounts;
    private int[] stackIds;
    private int team;
    private int tertiaryFemaleEquipmentModel = -1;
    private int tertiaryMaleEquipmentModel = -1;
    private int value = 1;

}

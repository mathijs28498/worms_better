package wb.gameObjects.weapons;

import wb.utils.Images;

import java.awt.image.BufferedImage;

public enum WeaponType {

    BASICROCKET(0, Images.basicRocket),
    FATTY(1, Images.fatty),
    HOMINGBOY(2, Images.homingBoy);

    private static int maxIndex = 1;
    private int index;
    private BufferedImage image;

    WeaponType(int index, BufferedImage image) {
        this.index = index;
        this.image = image;
    }

    public static WeaponType getNextWeapon(WeaponType type) {
        switch (type) {
            case BASICROCKET:
                return FATTY;
            case FATTY:
                return HOMINGBOY;
            case HOMINGBOY:
                return BASICROCKET;
        }
        return null;
    }

    public static WeaponType getPreviousWeapon(WeaponType type) {
        switch (type) {
            case BASICROCKET:
                return HOMINGBOY;
            case FATTY:
                return BASICROCKET;
            case HOMINGBOY:
                return FATTY;
        }
        return null;

    }

    public int getIndex() {
        return index;
    }

    public BufferedImage getImage() {
        return image;
    }
}

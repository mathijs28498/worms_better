package wb.gameObjects.weapons;

public enum WeaponType {

    BASICROCKET(0),
    FATTY(1);

    private static int maxIndex = 1;
    private int index;

    WeaponType(int index) {
        this.index = index;
    }

    public static WeaponType getNextWeapon(WeaponType type) {
        switch (type) {
            case BASICROCKET:
                return FATTY;
            case FATTY:
                return BASICROCKET;
        }
        return null;
    }

    public static WeaponType getPreviousWeapon(WeaponType type) {
        switch (type) {
            case BASICROCKET:
                return FATTY;
            case FATTY:
                return BASICROCKET;
        }
        return null;

    }





}

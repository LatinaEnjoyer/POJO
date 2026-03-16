package TextTurnBasedRogueLike;

public abstract class Item {
    protected String name;

    Item(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}

class DamageItem extends Item{
    int damage;
    DamageItem(String name, int damage){
        super(name);
        this.damage = damage;
    }
}

class DefenseItem extends Item{
    double damageMultiplier;
    DefenseItem(String name, double damageMultiplier){
        super(name);
        this.damageMultiplier = damageMultiplier;
    }


}

package client.model.skills;

public enum SkillProperty {
    //afterAttack
    GoToTarget,       //hero go to target
    StayOnSpot,       //hero stay on the spot

    //useDistance
    Lob,              //can use over uncrossable
    NoLob,            //have to sight target
    Flood,            //have to go to the target

    //rangeType
    PointRange,          //one field
    FloodRange,          //dfs, eg fireball
    AreaRange,           //Protagoras, eg earthquake

    Resurrection,   //casted on one point not necessarily next to the hero


}

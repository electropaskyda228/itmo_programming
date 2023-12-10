package main.java;

public class Life {
    public static void main(String[] args){
        FamilyFriend snusmumric = new FamilyFriend("Снусмумрик", true);
        FamilyMember mumi = new FamilyMember("Муми-тролль", true);
        Family family = new Family();
        family.addFamilyMember(mumi);

        Hat hatSnusmumric = new Hat("шляпа");
        hatSnusmumric.addAdjective("старая");
        Silhouette silhatSnusmumric = new Silhouette(hatSnusmumric);
        silhatSnusmumric.addAdjective("привычный");
        Tube tubeSnusmumric = new Tube("трубка");
        snusmumric.addStuff(hatSnusmumric);
        snusmumric.addStuff(tubeSnusmumric);

        System.out.println(StrangeString.makeFirst(DayTime.NIGHT.getDescription()));
        Sky sky = new Sky("небо");
        sky.addAdjective("ночное");
        System.out.println(StrangeString.makeFirst(sky.getFone()));
        System.out.println(StrangeString.makeFirst(StatusWaterPlace.SERIOUS_CALM.getDescription()));
        System.out.print(StrangeString.makeFirst(Duration.LONGTIME.getDescription()) + " " + snusmumric + " ");
        snusmumric.row();
        AdditionalAction.addAboveAction(", не произнося ни слова.");
        System.out.print(mumi + " ");
        mumi.see(silhatSnusmumric + " " + snusmumric);
        tubeSnusmumric.throughCircles(5);
        mumi.philosophicallyThink(makeQuote(true).quote("теперь все будет хорошо", "подумал", mumi.toString()));
    }

    private static OperationString makeQuote(boolean isFirst){
        if(isFirst) {
            return (what, how, who) -> "\"" + StrangeString.makeFirst(what) + "\", - " + how + " " + who;
        } else{
            return (what, how, who) -> who + " " + how + ": \"" + StrangeString.makeFirst(what) + "\"";
        }
    }
}

interface OperationString{
    String quote(String what, String how, String who);
}

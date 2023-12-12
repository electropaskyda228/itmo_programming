package main.java;
import java.util.Random;

public class Life {
    static class They extends Citizen {
        They(String name) {
            super(name, true);
        }
        public void beUnderRemb(String[] thing){
            this.mood = Mood.PENSIVE;
            String answer = "";
            for(int i=0; i < thing.length; i++){
                answer += thing[i];
                if(i == 0){
                    answer += ", ";
                } else if(i != thing.length - 1){
                    answer += " и ";
                }
            }
            System.out.print(this + " были под впечатлением " + answer);
        }
    }

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
        snusmumric.changePower(60);
        snusmumric.row(20);
        AdditionalAction.addAboveAction(", не произнося ни слова.");
        System.out.print(mumi + " ");
        mumi.see(silhatSnusmumric + " " + snusmumric);
        tubeSnusmumric.throughCircles(5);
        mumi.philosophicallyThink(makeQuote(true).quote("теперь все будет хорошо", "подумал", mumi.toString()));
        System.out.println("\n\n\n");

        new Entity("возгласы и аплодисменты") {
            {
                System.out.print(StrangeString.makeFirst(this.getName()) + " " + ableToHear() + " " +
                        PowerOfAction.ALL_WEAKER_AND_WEAKER.getDescription());
            }
            String ableToHear() {return "доноситься";}
        };

        class Splash extends Entity{
            Splash(String name) {
                super(name);
            }
            public String makeNoisy(){
                return "нарушать тишину";
            }
        }

        Splash splash = new Splash("всплески");
        Oar oar = new Oar("весла");
        System.out.println(SentenceMaker.bunchSentences("", splash + " " + oar + " " + splash.makeNoisy() + "."));
        Coast coasts = new Coast("берега");
        Straight darkStraight = new Straight("полоска");
        darkStraight.addAdjective("темная");
        System.out.println(StrangeString.makeFirst(makeConversionPast(false, true).turnInto(coasts.toString(), darkStraight.toString())));

        System.out.println("Собственно, " + doNotWant().notWant("говорить", "них") + ".");
        System.out.println("До поры до времени.");
        System.out.print(StrangeString.makeFirst(thereIsNothingTo().phraseNothing("спешить", "им")) + ": ");
        Random rnd = new Random();
        int number = rnd.nextInt(9) + 1;
        if(number == 9){
            throw new ThereIsNoSummer();
        }
        Summer summer = new Summer("лето");
        System.out.print(Direction.FORWARD.getDescription() + " ");
        summer.be(true);
        System.out.print(SentenceMaker.dependentConnection(false, false, true, "", ""));

        new Entity("надежды") {
            {
                summer.promise("исполнение всех " + this);
            }
        };

        System.out.print(".\nА " + DayTime.NOW.getDescription() + " ");

        Entity meeting = new Entity("встреча") {
            {
                this.addAdjective("драматическая");
            }
        };

        Entity anxiousThinking = new Entity("переживания"){};

        They they = new They("они");
        Escape escape = new Escape("побег");
        escape.addAdjective("опасный");
        String[] remembers = {meeting.toString(), anxiousThinking + " этой " + DayTime.NIGHT.getDescription(), escape.toString()};
        they.beUnderRemb(remembers);
        System.out.println(".\n" + StrangeString.makeFirst(enoughOfWho().enough(they.toString())) + ", к чему еще разговоры!");


        Boat boat = new Boat("лодка");
        System.out.print(StrangeString.makeFirst(boat.toString()) + " ");
        boat.startToCircle();
        AdditionalAction.addAboveAction(", направляясь к " + coasts + ".");
        Pizza pizza = new Pizza("пицца спасения");
        try{
            pizza.changePieces(0);
        } catch (ZeroPieces e){
            System.out.println(e.getMessage());
        }
    }

    private static OperationString makeQuote(boolean isFirst){
        if(isFirst) {
            return (what, how, who) -> "\"" + StrangeString.makeFirst(what) + "\", - " + how + " " + who;
        } else{
            return (what, how, who) -> who + " " + how + ": \"" + StrangeString.makeFirst(what) + "\"";
        }
    }
    private static TurnInto makeConversionPast(boolean sex, boolean many){
        if(many){
            return (from, to) -> from + " превратились в " + to;
        } else if(sex){
            return (from, to) -> from + " превратился в " + to;
        } else {
            return (from, to) -> from + " превратилась в " + to;
        }
    }
    private static NotWant doNotWant(){
        return (action, who) -> action + " у " + who + " не было охоты";
    }
    private static PhraseForNothing thereIsNothingTo(){
        return (action, who) -> who + " некуда было " + action;
    }
    private static EnoughOf enoughOfWho(){
        return (who) -> "с " + who + " было достаточно";
    }
}

@FunctionalInterface
interface OperationString{
    String quote(String what, String how, String who);
}
@FunctionalInterface
interface TurnInto{
    String turnInto(String from, String to);
}
@FunctionalInterface
interface NotWant{
    String notWant(String action, String who);
}
@FunctionalInterface
interface PhraseForNothing{
    String phraseNothing(String action, String who);
}
@FunctionalInterface
interface EnoughOf{
    String enough(String who);
}


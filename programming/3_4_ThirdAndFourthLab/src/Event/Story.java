package Event;

import java.util.*;

public class Story {
    private ArrayList<Shorty> shorties;
    private MainCharacter neznayka;
    private MainCharacter kozlik;
    private ArrayList<Location> locations;
    private SmallItem money;
    public Story(ArrayList<Shorty> shorties, MainCharacter neznayka, MainCharacter kozlik, ArrayList<Location> locations) {
        this.shorties = shorties;
        this.neznayka = neznayka;
        this.kozlik = kozlik;
        this.locations = locations;
        money = new SmallItem(new Location("Небытие",Integer.MAX_VALUE),"Деньги", 100);
    }

    public void go(){
        if(Math.random() < shorties.stream().map(Shorty::getLuck).reduce(1.0, (a,b) -> a * b)*2){

            this.kozlik.addToInv(money);
            this.neznayka.addToInv(money);
        }else{
            locations.get(0).addToInv(money);
        }

        for (Shorty shorty : shorties) {
            if (shorty.searchForItem(this.money)){
                System.out.println("Коротышки нашли деньги и все жили долго и счастливо.");
                return;
            }
        }
        System.out.println("Убедившись, что денег нигде нет, коротышки рассвирепели.");

        while (!shorties.get(0).getLocation().getInventory().isEmpty()){
            Item item = shorties.get(0).getLocation().getInventory().get(shorties.get(0).getLocation().getInventory().size() - 1);

            for (Shorty shorty : shorties){
                try {
                    shorty.destroy(item);
                }catch (ItemNotFoundException e){
                    System.out.println(e.getMessage());
                }

            }
        }
        System.out.println("Наконец они посмотрели в окно и увидели веревку, свешивавшуюся вниз.");

        for (Shorty shorty : shorties){
            if (shorty.getLuck() > 0.5){
                shorty.move(shorty.getLocation().getNeighbors().get(0));
            }else{
                shorty.move(shorty.getLocation().getNeighbors().get(1));
            }
            System.out.println(shorty.getName() + " cпустился по " + shorty.getLocation().getName());
        }

        Shorty leastEnergyShorty = null;
        MainCharacter leastEnergyMC = (neznayka.getEnergy() < kozlik.getEnergy() ? neznayka : kozlik);
        for (Shorty shorty : shorties) {
            if (leastEnergyShorty == null || shorty.getEnergy() < leastEnergyMC.getEnergy()){
            leastEnergyShorty = shorty;
            }
        }

        neznayka.move();
        kozlik.move();
        for (Shorty shorty : shorties) {
            if (shorty.move(leastEnergyMC.getLocation())){
                System.out.println(shorty.getName() + " догнал " + leastEnergyMC.getName() + " и забрал деньги, смешался с толпой и вскоре был далеко от места происшествия.");
                return;
            }
        }
        System.out.println("Но было поздно. Незнайки и козлика и след простыл.");
        PathResult path = PathFinder.findShortestPath(locations.get(0), leastEnergyMC.getLocation());
        System.out.print("Коротышки пробежали через ");
        path.path().forEach(x -> System.out.print(x.getName() + ", "));
        System.out.println("Очутившись на другой улице, они смешались с толпой и вскоре были далеко от места происшествия.");
        }

    }



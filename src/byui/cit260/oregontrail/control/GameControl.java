package byui.cit260.oregontrail.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import byui.cit260.oregontrail.view.*;
import byui.cit260.oregontrail.exceptions.GameControlException;
import byui.cit260.oregontrail.exceptions.MapControlException;
import byui.cit260.oregontrail.model.*;
import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameControl implements Serializable
{
    private static final long serialVersionUID = 1L; 
    private static Game game;
    
    public GameControl() {
        // Default constructor.
    }
    
    MapControl mapControl = new MapControl();

    public Game createNewGame() throws MapControlException {
        
        // Create new game.
        game = new Game();
        game.clear(); // Reset values of all game attributes to default / null.
        
        // Get player name.
        StartProgram program = new StartProgram();
        String playerName = program.getPlayerName();
        
        // Create new player.
        createPlayer(playerName);
        
        // Create player actors
        Occupations.createActors("Default1", "Default2", "Default3");
        
        game.setSetup(new SetupView());
        SetupView setup = game.getSetup();

        // Request & set player occupation.
        String occupationName = setup.requestOccupation();
        game.setPlayerOccupationName(occupationName);

        // Request party member names.
        setup.requestNames();
        game.setPartyMember1(setup.getMember1());
        game.setPartyMember2(setup.getMember2());
        game.setPartyMember3(setup.getMember3());
        
        // Assign player occupation object to current game.
        Occupations occupations = setup.getPlayerOccupation();
        game.setOccupation(occupations);
        
        // Create inventory & assign to current game.
        game.setInventory(new Inventory());
        
        // Create NPC actor array & item array, store in Game class (model).
        game.setGameItems(createItems());
        InventoryItem[] gameItems = game.getGameItems();
        
        game.setNPCActors(createActors());
        
        // Create new map and store in Game class (model).
        MapControl controlMap = new MapControl();
        Map map = controlMap.createMap(10, 10, gameItems);
        if (map == null) {
            throw new MapControlException("Failed to create new map.");
        }
        else {
            game.setMap(map);           
        }
        
        // Create questions, assign to current game.
        game.setQuestionScenes(controlMap.getQuestionScenes());
        
        // Assign scene coordinates to current game.
        game.setCoordinatePairs(controlMap.getPairs());
        
        
        // Request month, assign to current game.
        StartMonthView months = new StartMonthView();
        months.display();
        
        String currentMonth = months.getCurrentMonth();
        game.setCurrentMonth(currentMonth);
        
        // Store new game in MainMenu (model).
        MainMenu.setGame(game);
        
        // Update current game.
        OregonTrail.setCurrentGame(game);
        
        System.out.println("\nA new game has been created.\n");

        new GeneralStoreView();
        
        return game;
    }
    
     public static Player createPlayer(String name) {
        
         if (name == null) {
            return null;
        }

        Player playerOne = new Player();
        playerOne.setPlayerName(name); // Assign name to player object.
        
        game.setPlayer(playerOne);
        game.getPlayer().setPlayerName(name); // Assign name to playerName variable in game class.
        
        OregonTrail.setPlayer(playerOne);
        
        return playerOne; // Return player object.
    }
    
    public static Actor[] createActors() {

        Actor[] actors = new Actor[10];

        // String occupation, String name, String description, Point coordinates, double money, double health
        actors[ActorType.Lehi.ordinal()] = new Actor("Pioneer", "Lehi", "Lehi", new Point(1,2), 1000, 100, false);
        actors[ActorType.Sariah.ordinal()] = new Actor("Pioneer", "Sariah", "Sariah", new Point(1,2), 1000, 100, false);
        actors[ActorType.Nephi.ordinal()] = new Actor("Pioneer", "Nephi", "Leader of Nephites", new Point(1,2), 1000, 100, false);
        actors[ActorType.Jacob.ordinal()] = new Actor("Pioneer", "Jacob", "Jacob", new Point(1,2), 1000, 100, false);
        actors[ActorType.Sam.ordinal()] = new Actor("Pioneer", "Sam", "Sam", new Point(1,2), 1000, 100, false);
        actors[ActorType.Laman.ordinal()] = new Actor("Pioneer", "Laman", "Laman", new Point(1,2), 1000, 100, false);
        actors[ActorType.Lemuel.ordinal()] = new Actor("Pioneer", "Lemuel", "Lemuel", new Point(1,2), 1000, 100, false);
        actors[ActorType.Zoram.ordinal()] = new Actor("Pioneer", "Zoram", "Zoram", new Point(1,2), 1000, 100, false);
        actors[ActorType.Angel.ordinal()] = new Actor("Pioneer", "Angel", "Angel", new Point(1,2), 1000, 100, false);
        actors[ActorType.Lord.ordinal()] = new Actor("Pioneer", "Lord", "Lord", new Point(1,2), 1000, 100, false);
        
        game.setPcActors(actors);
        return actors;
    }
    
    public static void visitCalc() {
        
        SumVisitor sumVisitor = new SumVisitor();
        MinVisitor minVisitor = new MinVisitor();
        MaxVisitor maxVisitor = new MaxVisitor();
            
        for (Actor person : game.getPcActors()) {
            sumVisitor.visitElement(person);
            minVisitor.visitElement(person);
            maxVisitor.visitElement(person);
        }
        System.err.println("\nSum: " + sumVisitor.getSum());
        System.err.println("Min: " + minVisitor.getMin());
        System.err.println("Max: " + maxVisitor.getMax());
        
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static InventoryItem[] createItems() {
       InventoryItem[] items = new InventoryItem[15];
       
       InventoryItem lumber = new InventoryItem("lumber", 200);
       InventoryItem ore = new InventoryItem("ore", 200);
       InventoryItem grain = new InventoryItem("grain", 200);
       InventoryItem legume = new InventoryItem("legume", 200);
       InventoryItem oil = new InventoryItem("oil", 200);
       InventoryItem water = new InventoryItem("water", 200);
       InventoryItem honey = new InventoryItem("honey", 200);
       InventoryItem salt = new InventoryItem("salt", 200);
       InventoryItem axe = new InventoryItem("axe", 200);
       InventoryItem hammer = new InventoryItem("hammer", 200);
       InventoryItem drill = new InventoryItem("drill", 200);
       InventoryItem shovel = new InventoryItem("shovel", 200);
       InventoryItem sickle = new InventoryItem("sickle", 200);
       InventoryItem saw = new InventoryItem("saw", 200);       
       InventoryItem nails = new InventoryItem("nails", 200);
       
       items[ItemType.lumber.ordinal()] = lumber;
       items[ItemType.ore.ordinal()] = ore;
       items[ItemType.grain.ordinal()] = grain;
       items[ItemType.legume.ordinal()] = legume;
       items[ItemType.oil.ordinal()] = oil;
       items[ItemType.water.ordinal()] = water;
       items[ItemType.honey.ordinal()] = honey;
       items[ItemType.salt.ordinal()] = salt;
       items[ItemType.axe.ordinal()] = axe;
       items[ItemType.hammer.ordinal()] = hammer;
       items[ItemType.drill.ordinal()] = drill;
       items[ItemType.shovel.ordinal()] = shovel;
       items[ItemType.sickle.ordinal()] = sickle;
       items[ItemType.saw.ordinal()] = saw;
       items[ItemType.nails.ordinal()] = nails;
       
       return items;
    }
   
    public static Game getGame() {
        return game;
    }

    public static void setGame(Game gameSet) {
        game = gameSet;
    }

    public static void saveGame(Game currentGame, String filePath) throws GameControlException {
        
        try (FileOutputStream fops = new FileOutputStream (filePath)) {
            ObjectOutputStream output = new ObjectOutputStream(fops);
            
            output.writeObject(currentGame);
        }
        catch (Exception e) {
            throw new GameControlException(e);
        }
    }

    public static void getSavedGame(String filePath) throws GameControlException {

        Game game = null;
        
        try (FileInputStream fips = new FileInputStream(filePath)) {
            ObjectInputStream input = new ObjectInputStream(fips);
            
            game = (Game) input.readObject();
        }
        catch (Exception e) {
            throw new GameControlException(e);
        }
        
        OregonTrail.setCurrentGame(game);
        Game loadedGame = OregonTrail.getCurrentGame();
        TravelView.setSceneIndex(loadedGame.getSceneIndex());
    }
}

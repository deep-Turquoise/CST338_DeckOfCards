// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Haley Dimapilis
// CST338-30_SU17: Software Design
// Module 3
// Write a Java program: Decks of Cards (4 hrs)

import java.util.Random;

public class Assig3
{
   public static void main(String[] args)
   {
      System.out.println("----------------------------------------------------");
      System.out.println("Testing for Class Card...");
      
      Card legalCard1 = new Card('2', Card.Suit.diamonds);
      Card legalCard2 = new Card('5', Card.Suit.hearts);
      Card illegalCard1 = new Card('Z', Card.Suit.hearts);
      
      System.out.println(legalCard1.toString());
      System.out.println(legalCard2.toString());
      System.out.println(illegalCard1.toString());

      System.out.println(); // spacer

      // set card 1 to something illegal and set illegalCard1 to something legal
      legalCard1.set('C', Card.Suit.diamonds);
      illegalCard1.set('A', Card.Suit.clubs);

      System.out.println(legalCard1.toString());
      System.out.println(legalCard2.toString());
      System.out.println(illegalCard1.toString());
      
      System.out.println("----------------------------------------------------");
      System.out.println("Testing for Class Deck...");
      Deck newDeck = new Deck(2);
      int count = 0;
      while(true)
      {
         System.out.print(count + ". ");
         ++count;
         Card dealCard = new Card();
         dealCard = newDeck.dealCard();
         if(dealCard == null) { break; }
         else{ System.out.println(dealCard.toString()); }
      }
   }
}

/*
 * This class is a simple representation of a card object.
 * It carries only a suit and a value, as well as an internal error flag.
 */
class Card
{
   public enum Suit {clubs, diamonds, hearts, spades}
   public static char[] values = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
   private char value;
   private Suit suit;
   private boolean errorFlag;

   /*
    * The basic constructor for the card class.
    * It initializes the card to the Ace of Spades
    */
   Card()
   {
      value = 'A';
      suit = Suit.spades;
      errorFlag = false;
   }

   /* Card constructor for creating a deep copy.
    * All of the internal types for this class are scalers and do not require
    * any deep copy mechanisms.
    */
   Card(Card copyCard)
   {
      value = copyCard.getValue();
      suit = copyCard.getSuit();
      errorFlag = copyCard.getErrorFlag();
   }

   /*
    * Another Constructor for the Card class which can be called with a suit and a value.
    * This constructor checks for errors and returns a card initiated with "error=True" if either value is bad.
    * The settings for the value and suit are undefined when a bad values are given, so the errorFlag should be checked
    * when you make a new card to ensure that a valid object was returned.
    */
   Card(char value, Suit suit)
   {
      boolean goodCard;
      goodCard = set(value, suit);
      if (goodCard)
      {
         errorFlag = false;
      }
      else
      {
         errorFlag = true;
      }
   }

   // Simple Accessor for value
   public char getValue()
   {
      return value;
   }

   // Simple Accessor for suit
   public Suit getSuit()
   {
      return suit;
   }

   // Simple Accessor for errorFlag
   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   /*
    * This Method sets the value and suit for the card.
    * It checks the arguments and returns true when they are correct, false otherwise.
    * It also sets the errorFlag based on the correctness of the arguments.
    * Returns: boolean
    */
   public boolean set(char newValue, Suit newSuit)
   {
      if (isValid(newValue, newSuit))
      {
         value = newValue;
         suit = newSuit;
         errorFlag = false;
      }
      else
      {
         errorFlag = true;
         suit = newSuit;
      }
      return !errorFlag;
   }

   /*
    * This method generates, then returns a string representation of the card.
    */
   public String toString()
   {
      if (errorFlag == true)
      {
         return "[Invalid]";
      }
      return getValue() + " of " + getSuit();
   }

   /*
    * This method takes an argument for value and suit and returns true if they are acceptable.
    * Returns: boolean
    */
   private boolean isValid(char value, Suit suit)
   {
      for (int i = 0; i < values.length; i++)
      {
         if (value == values[i])
         {
            return true;
         } 
      }
      return false;
   }
}
class Hand
{
   public static int MAX_CARDS = 50;
   private Card[] myCards;
   private int numCards;

   Hand()
   {
      numCards = 0;
      myCards = new Card[MAX_CARDS];
   }

   public void resetHand()
   {
      for (int i = 0; i < numCards; i++)
      {
         myCards[i] = null;
         numCards = 0;
      }
   }

   public boolean takeCard(Card card)
   {
      if (numCards != MAX_CARDS)
      {
         myCards[numCards] = card;
         numCards++;
         return true;
      }
      return false;
   }

   public Card playCard()
   {
      Card retCard = myCards[numCards];
      myCards[numCards] = null;
      numCards--;
      return retCard;
   }

   public String toString()
   {
      String hand = "Hand:\n";

      for (int i = 0; i < numCards; i++)
      {
         hand += myCards[i].toString() + "\n";
      }

      return hand;
   }

   public int getNumCards()
   {
      return numCards;
   }

   // Tests to see if card is legal or not
   public Card inspectCard(int k)
   {
	  if(numCards == 0 || k < 0 || k > numCards)
	  {
	     // Creates illegal card
	     return new Card('X', Card.Suit.spades);
	  }
	  else 
	  {
	     return myCards[k];
	  }
   }
}

class Deck
{
   public final int MAX_CARDS = 6 * 52; // allow a maximum of six packs (6×52 cards)
   private static Card[] masterPack;
   private Card[] cards = new Card[MAX_CARDS];
   private int topCard;
   private int numPacks;
   
   Deck()
   {
      allocateMasterPack();
      for(int x = 0; x < masterPack.length; ++x)
      {
         cards[x] = masterPack[x];
      }
   }

   Deck(int numPacks)
   {
      allocateMasterPack();
      // to ensure we are not going over max
      if(numPacks > MAX_CARDS) { numPacks = MAX_CARDS; }
      
      int count = numPacks;
      while(count > 0) 
      {
         for(int x = 0; x < masterPack.length; ++x)
         {
               cards[((numPacks-count)*52)+x] = masterPack[x];
         }
         --count;
      }
   }

   public void init(int numPacks)
   {
      allocateMasterPack();
      for(int x = 0; x < cards.length; ++x)
      {
         cards[x] = null;
      }

      int count = numPacks;
      while(count > 0) 
      {
         for(int x = 0; x < masterPack.length; ++x)
         {
               cards[((numPacks-count)*52)+x] = masterPack[x];
         }
         --count;
      }
   }

   public void shuffle() 
   {
      int index;
      Card temp = new Card();
      Random random = new Random();
      for (int i = cards.length - 1; i > 0; i--)
      {
         index = random.nextInt(i + 1);
         temp = cards[index];
         cards[index] = cards[i];
         cards[i] = temp;
      }
   }

   public int topCardAccessor()
   {
      int intReturn = cards.length-1;
      for(int x = cards.length-1; x > 0; --x)
      {
         if(cards[x] != null)
         {
            intReturn = x;
            break;
         }
         else { continue; }
      }
      return intReturn;
   }

   public Card dealCard() 
   {
      topCard = topCardAccessor();
      Card newCard = new Card();
      newCard.set(cards[topCard].getValue(), cards[topCard].getSuit());
      cards[topCard] = null; // remove card
      return newCard;
   }

   public Card inspectCard(int k)
   {
      if(k > topCardAccessor() || cards[k] == null)
      {
         Card badCard = new Card();
         badCard.set('z', Card.Suit.clubs); // this will receive an error flag because of "z"
         return badCard;
      }
      else { return cards[k]; }
   }

   private static void allocateMasterPack()
   {
      if(masterPack== null)
      {
         masterPack = new Card[52];
         int count = 0;
         for(int i = 0; i < Card.Suit.values().length; ++i)
         {
            for(int x = 0; x < Card.values.length; ++x)
            {
               masterPack[count] = new Card();
               masterPack[count].set(Card.values[x], Card.Suit.values()[i]);
               ++count;
            }
         }
      }
   }
}

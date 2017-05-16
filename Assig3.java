// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Hayley Dimapillis
// CST338-30_SU17: Software Design
// Module 3
// Write a Java program: Decks of Cards (4 hrs)

import java.util.Random;

public class Assig3
{
   public static void main(String[] args)
   {
      // class Card test
      Card legalCard1 = new Card('2', Card.Suit.diamonds);
      Card legalCard2 = new Card('5', Card.Suit.hearts);
      Card illegalCard1 = new Card('Z', Card.Suit.hearts);
      System.out.println("----------------------------------------------------");
      System.out.println("Testing for Class Card...");
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
   }
}

class Card
{
   public enum Suit {clubs, diamonds, hearts, spades}
   public static char[] values = {'A','2','3','4','5','6','7','8','9','T','J','Q','K'};
   private char value;
   private Suit suit;
   private boolean errorFlag;

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

   Card(char value, Suit suit)
   {
      set(value, suit); 
   }

   public char getValue()
   {
      return value;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean getErrorFlag()
   {
      return errorFlag;
   }

   public boolean set(char newValue, Suit newSuit)
   {
      if (isValid(newValue, newSuit) == true)
      {
         value = newValue;
         suit = newSuit;
         errorFlag = false;
         return true;
      }
      else
      {
         errorFlag = true;
         suit = newSuit;
         return false;
      }     
   }

   public String toString()
   {
      if (errorFlag == true)
      {
         return "[Invalid]";
      }
      return getValue() + " of " + getSuit();
   }

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

   //    public Card inspectCard(int k)
   //    {
   //       //I am confused about this one - Christopher
   //    }
}

class Deck
{
   public final int MAX_CARDS = 6 * 52; // allow a maximum of six packs (6×52 cards)
   private static Card[] masterPack = new Card[52];
   private Card[] cards = new Card[MAX_CARDS];
   private int topCard;
   private int numPacks;

   Deck()
   {
      int count = 0;
      for(int i = 0; i < Card.Suit.values().length; ++i)
      {
         for(int x = 0; x < Card.values.length; ++x)
         {
            Card newCard = new Card();
            newCard.set(Card.values[x], Card.Suit.values()[i]);
            ++count;
            cards[count] = newCard;
         }
      }
   }

   Deck(int numPacks)
   {
      // to ensure we are not going over max
      if(numPacks > MAX_CARDS)
      {
         numPacks = MAX_CARDS;
      }

      for(int x = 0; x < numPacks; ++x)
      {
         Deck newDeck = new Deck();
      }
   }

   public void init(int numPacks)
   {
      Deck newDeck = new Deck(numPacks);
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
      }
      return intReturn;
   }

   public Card dealCard() 
   {
      int topCard = topCardAccessor();
      Card newCard = cards[topCard];
      newCard.set(cards[topCard].getValue(), cards[topCard].getSuit());
      cards[topCard] = null;
      return newCard;
   }
}
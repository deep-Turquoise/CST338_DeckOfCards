// Nicholas Nelson, David Henderson, Christopher Calderon
// Nathan Mauga, Hayley Dimapillis
// CST338-30_SU17: Software Design
// Module 3
// Write a Java program: Decks of Cards (4 hrs)

public class Assig3
{

}

public class Card
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

public class Deck
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
      return myCards[--numCards];
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
}

public class Hands
{

}


import java.util.Arrays;
import java.util.Scanner;

public class PokerHands {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        boolean isTie = false;
        while (!isTie) {
            // get player 1's hand
            String[] player1Hand = getPlayerHand(input, "Black");

            // get player 2's hand
            String[] player2Hand = getPlayerHand(input, "White");

            // determine the winner
            int result = compareHands(player1Hand, player2Hand);
            if (result > 0) {
                System.out.println("Black wins!");
            } else if (result < 0) {
                System.out.println("White wins!");
            } else {
                isTie = true;
                System.out.println("It's a tie!");
            }
        }
    }

    private static String[] getPlayerHand(Scanner input, String playerName) {
        System.out.println(playerName + ", enter your five-card hand (e.g. 2H 3C 4D 5S 6H):");
        String[] hand = input.nextLine().toUpperCase().split(" ");
        while (hand.length != 5) {
            System.out.println("Invalid input. Please enter exactly five cards separated by spaces.");
            hand = input.nextLine().toUpperCase().split(" ");
        }
        return hand;
    }

    private static int compareHands(String[] hand1, String[] hand2) {
        Arrays.sort(hand1);
        Arrays.sort(hand2);
        int rank1 = getHandRanking(hand1);
        int rank2 = getHandRanking(hand2);
        if (rank1 > rank2) {
            return 1;
        } else if (rank1 < rank2) {
            return -1;
        }
        else {
            return compareTies(hand1, hand2, rank1);
        }
    }

    private static int compareTies(String[] hand1, String[] hand2, int rank) {
        switch (rank) {
            case 8:
                return compareFourOfAKind(hand1, hand2);
            case 7:
                return compareFullHouse(hand1, hand2);
            case 4:
                return compareThreeOfAKind(hand1, hand2);
            case 3:
                return compareTwoPair(hand1, hand2);
            case 2:
                return compareOnePair(hand1, hand2);
            default:
                return compareHighCard(hand1, hand2);
        }
    }

    private static int getHandRanking(String[] hand) {
        if (isStraightFlush(hand)) {
            System.out.println("This is a Straight Flush");
            return 9;
        } else if (isFourOfAKind(hand)) {
            System.out.println("This is a Four Of A Kind");
            return 8;
        } else if (isFullHouse(hand)) {
            System.out.println("This is a Full House");
            return 7;
        } else if (isFlush(hand)) {
            System.out.println("This is a Flush");
            return 6;
        } else if (isStraight(hand)) {
            System.out.println("This is a Straight");
            return 5;
        } else if (isThreeOfAKind(hand)) {
            System.out.println("This is a Three Of A Kind");
            return 4;
        } else if (isTwoPair(hand)) {
            System.out.println("This is A Two Pair");
            return 3;
        } else if (isOnePair(hand)) {
            System.out.println("This is A One Pair");
            return 2;
        } else {
            return 1;
        }
    }

    private static int compareOnePair(String[] hand1, String[] hand2) {
        int[] rankValues1 = getRankValues(getCardRanks(hand1));
        int[] rankValues2 = getRankValues(getCardRanks(hand2));

        int pairValue1 = 0;
        int pairValue2 = 0;
        int kicker1 = 0;
        int kicker2 = 0;

        for (int i = rankValues1.length - 1; i >= 0; i--) {
            if (rankValues1[i] == 2) {
                pairValue1 = i;
            } else if (rankValues1[i] == 1) {
                if (kicker1 == 0) {
                    kicker1 = i;
                }
            }

            if (rankValues2[i] == 2) {
                pairValue2 = i;
            } else if (rankValues2[i] == 1) {
                if (kicker2 == 0) {
                    kicker2 = i;
                }
            }
        }

        if (pairValue1 > pairValue2) {
            return 1;
        } else if (pairValue1 < pairValue2) {
            return -1;
        } else {
            if (kicker1 > kicker2) {
                return 1;
            } else if (kicker1 < kicker2) {
                return -1;
            } else {
                return 0;
            }
        }
    }
    private static int compareTwoPair(String[] hand1, String[] hand2) {
        int[] rankValues1 = getRankValues(getCardRanks(hand1));
        int[] rankValues2 = getRankValues(getCardRanks(hand2));

        int highPair1 = 0;
        int lowPair1 = 0;
        int highPair2 = 0;
        int lowPair2 = 0;
        int kicker1 = 0;
        int kicker2 = 0;

        for (int i = rankValues1.length - 1; i >= 0; i--) {
            if (rankValues1[i] == 2) {
                if (highPair1 == 0) {
                    highPair1 = i;
                } else if (lowPair1 == 0) {
                    lowPair1 = i;
                }
            } else if (rankValues1[i] == 1) {
                if (kicker1 == 0) {
                    kicker1 = i;
                }
            }

            if (rankValues2[i] == 2) {
                if (highPair2 == 0) {
                    highPair2 = i;
                } else if (lowPair2 == 0) {
                    lowPair2 = i;
                }
            } else if (rankValues2[i] == 1) {
                if (kicker2 == 0) {
                    kicker2 = i;
                }
            }
        }

        if (highPair1 > highPair2) {
            return 1;
        } else if (highPair1 < highPair2) {
            return -1;
        } else {
            if (lowPair1 > lowPair2) {
                return 1;
            } else if (lowPair1 < lowPair2) {
                return -1;
            } else {
                if (kicker1 > kicker2) {
                    return 1;
                } else if (kicker1 < kicker2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    private static int compareThreeOfAKind(String[] hand1, String[] hand2) {
        int rankValue1 = getRankValues(getCardRanks(hand1))[2];
        int rankValue2 = getRankValues(getCardRanks(hand2))[2];

        if (rankValue1 > rankValue2) {
            return 1;
        } else if (rankValue1 < rankValue2) {
            return -1;
        } else {
            return compareHighCard(hand1, hand2);
        }
    }

    private static int compareFullHouse(String[] hand1, String[] hand2) {
        int rankValue1 = getRankValues(getCardRanks(hand1))[2];
        int rankValue2 = getRankValues(getCardRanks(hand2))[2];

        if (rankValue1 > rankValue2) {
            return 1;
        } else if (rankValue1 < rankValue2) {
            return -1;
        } else {
            return compareHighCard(hand1, hand2);
        }
    }

    private static int compareHighCard(String[] hand1, String[] hand2) {
        int[] values1 = getRankValues(getCardRanks(hand1));
        int[] values2 = getRankValues(getCardRanks(hand2));

        Arrays.sort(values1);
        Arrays.sort(values2);

        System.out.println("High card ");
        for (int i = 4; i >= 0; i--) {
            if (values1[i] > values2[i]) {
                return 1;
            } else if (values1[i] < values2[i]) {
                return -1;
            }
        }
        return 0;
    }

    private static int compareFourOfAKind(String[] hand1, String[] hand2) {
        // get the higher card composing the four of a kind
        int rankValue1 = getRankValues(getCardRanks(hand1))[0];
        int rankValue2 = getRankValues(getCardRanks(hand2))[0];

        if (rankValue1 > rankValue2) {
            return 1;
        } else if (rankValue1 < rankValue2) {
            return -1;
        } else {
            return compareHighCard(hand1, hand2);
        }
    }

    private static boolean isStraightFlush(String[] hand) {
        String[] suits = getCardSuits(hand);
        String[] ranks = getCardRanks(hand);
        return isFlush(hand) && isStraight(ranks);
    }

    private static boolean isFourOfAKind(String[] hand) {
        String[] ranks = getCardRanks(hand);
        for (int i = 0; i < ranks.length; i++) {
            if (countRank(ranks, ranks[i]) == 4) {
                return true;
            }
        }
        return false;
    }

    private static boolean isFullHouse(String[] hand) {
        String[] ranks = getCardRanks(hand);
        return countPairs(ranks) > 1 && countThreeOfAKind(ranks) > 1;
    }

    private static boolean isFlush(String[] hand) {
        String[] suits = getCardSuits(hand);
        return suits[0].equals(suits[1]) && suits[1].equals(suits[2])
                && suits[2].equals(suits[3]) && suits[3].equals(suits[4]);
    }

    private static boolean isStraight(String[] ranks) {
        int[] values = getRankValues(ranks);
        Arrays.sort(values);
        return values[0] == values[1] - 1 && values[1] == values[2] - 1
                && values[2] == values[3] - 1 && values[3] == values[4] - 1;
    }

    private static boolean isThreeOfAKind(String[] hand) {
        String[] ranks = getCardRanks(hand);
        return countThreeOfAKind(ranks) == 1 && countPairs(ranks) == 0;
    }

    private static boolean isTwoPair(String[] hand) {
        String[] ranks = getCardRanks(hand);
        return countPairs(ranks) == 2;
    }

    private static boolean isOnePair(String[] hand) {
        String[] ranks = getCardRanks(hand);
        return countPairs(ranks) == 1;
    }

    private static int countRank(String[] ranks, String rank) {
        int count = 0;
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                count++;
            }
        }
        return count;
    }

    private static int countPairs(String[] ranks) {
        int count = 0;
        for (int i = 0; i < ranks.length; i++) {
            if (countRank(ranks, ranks[i]) == 2) {
                count++;
            }
        }
        return count;
    }

    private static int countThreeOfAKind(String[] ranks) {
        int count = 0;
        for (int i = 0; i < ranks.length; i++) {
            if(countRank(ranks, ranks[i]) == 3) {
                count++;
            }
        }
        return count;
    }

    private static String[] getCardSuits(String[] hand) {
        String[] suits = new String[5];
        for (int i = 0; i < hand.length; i++) {
            suits[i] = hand[i].substring(1);
        }
        return suits;
    }

    private static String[] getCardRanks(String[] hand) {
        String[] ranks = new String[5];
        for (int i = 0; i < hand.length; i++) {
            ranks[i] = hand[i].substring(0, 1);
        }
        return ranks;
    }

    private static int[] getRankValues(String[] ranks) {
        int[] values = new int[5];
        for (int i = 0; i < ranks.length; i++) {
            String rank = String.valueOf(ranks[i].charAt(0));
            if (rank.equals("A")) {
                values[i] = 14;
            } else if (rank.equals("K")) {
                values[i] = 13;
            } else if (rank.equals("Q")) {
                values[i] = 12;
            } else if (rank.equals("J")) {
                values[i] = 11;
            } else if (rank.equals("10")) {
                values[i] = 10;
            } else {
                values[i] = Integer.parseInt(String.valueOf(rank.charAt(0)));
            }
        }
        return values;
    }
}
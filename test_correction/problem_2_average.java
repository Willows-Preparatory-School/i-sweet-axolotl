public class main {
    public static void main(String[] args)
    {
        int bio = 11;
        int chem = 4;
        int compSci = 28;
        int english = 37;
        int spanish = 37;

        int numOfClasses = 5;

        double average = bio + chem + compSci + english + spanish;
        average = average / numOfClasses;

        System.out.println("The average size is: " + average);
    }
}

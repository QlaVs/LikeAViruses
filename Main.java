import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main (String[] args) throws IOException{

        Calc calculator = new Calc();
        calculator.MakeReport();

    }
}

class Calc{
    String[] Name = new String[3];
    int[] Pay = new int[3];
    int[] Long = new int[3];
    int[] PayAll = new int[3];
    int Virus;

    void MakeReport() throws IOException{
        Scanner input = new Scanner(System.in);
        System.out.print("Выберите вирус (1=Логическая бомба, 2=Logger, 3=Joker, 4=Реклама, 5=Майнер, 0=Без вирусов): ");
        Virus = input.nextInt();

        for (int i = 0; i < 3; i++){
            int j = i + 1;

            //Этап 1
            System.out.print("Input a Name " + j + ": ");
            Name[i] = input.next();

            System.out.print("Input a Pay " + j + ": ");
            Pay[i] = input.nextInt();

            System.out.print("Input a Long " + j + ": ");
            Long[i] = input.nextInt();

            //Этап 2
            PayAll[i] = Pay[i] * Long[i];
        }

        switch (Virus) {
            case 1 -> LogicBomb();
            case 2 -> Logger();
        }

        //Этап 3
        System.out.println("№   Name    Pay    Long    PayAll");
        for (int i = 0; i < 3; i++){
            int j = i + 1;
            System.out.format("%1d%10s%5d%5d%7d\n", j, Name[i], Pay[i], Long[i], PayAll[i]);
        }

        switch (Virus) {
            case 3 -> Joker();
            case 4 -> Add();
            case 5 -> Miner();
        }

        //Этап 4
        System.out.print("Repeat?(y/n): ");
        if (input.next().equals("y")||input.next().equals("Y")){
            MakeReport();
        }
    }

    void LogicBomb(){
        for (int i = 0; i < 3; i++){
            if (Name[i].matches("Petrov|Lunin|Kolomiytsev|Петров|Лунин|Коломийцев")){
                PayAll[i] = PayAll[i] * 2;
            }
        }
    }

    void Logger() throws FileNotFoundException {
        File file = new File("Log.txt");
        PrintStream writer = new PrintStream(file);
        System.setOut(writer);
        System.out.println("№   Name    Pay    Long    PayAll");
        for (int i = 0; i < 3; i++) {
            int j = i + 1;
            System.out.format("%1d%10s%5d%5d%7d\n", j, Name[i], Pay[i], Long[i], PayAll[i]);
        }
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        System.out.println("**You data has been logged**");
    }

    void Joker(){
        for (int i = 0; i < 3; i++){
            if (PayAll[i] == 777){
                System.out.println("The " + Name[i] + " is blessed!!");
            }
        }
    }

    void Add() {
        int j = 0;
        for (int i = 0; i < 3; i++) {
            if (PayAll[i] > j){
                j = PayAll[i];
            }
        }
        System.out.println("Купите нашего слона всего за " + j + "!!!");
    }

    void Miner(){
        for (int i = 0; i < 3; i++){
            //if (Name[i].equals("Petrov") || Name[i].equals("Lunin") || Name[i].equals("Kolomiytsev")){
            if(Name[i].matches("Petrov|Lunin|Kolomiytsev|Петров|Лунин|Коломийцев")){
                String st = Name[i];

                MessageDigest messageDigest;
                byte[] digest = new byte[0];

                try {
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.reset();
                    messageDigest.update(st.getBytes());
                    digest = messageDigest.digest();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                BigInteger bigInt = new BigInteger(1, digest);
                StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

                while( md5Hex.length() < 32 ){
                    md5Hex.insert(0, "0");
                }

                try(FileWriter writer = new FileWriter("Mainer.txt", true))
                {
                    // запись строки
                    writer.write(md5Hex + "\n");
                    writer.flush();
                }
                catch(IOException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
        System.out.println("Miner activated...");
    }
}
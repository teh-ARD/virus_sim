package com.github.teh_ard;

import com.github.teh_ard.person.*;
import com.github.teh_ard.simulation.Simulation;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

// https://github.com/teh-ARD/virus_sim

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Pyta użytkownika o wartości na jakich symulacja ma działać, zaraża pierwsze osoby oraz odpala symulacje na
     * tych parametrach
     * @param args argumenty podane do programu
     */
    public static void main(String[] args) {

//      TODO: ew. zapytanie "czy chcesz zmieniać domyślne wartości?" żeby nie było tyle klikania enterem

        System.out.println("Podaj rozmiar mapy (domyślnie: 100, min: 10)");
        Simulation sim = new Simulation(Math.max(readInt(100),10));

        System.out.println("Podaj wskaźnik śmiertelności wirusa [1-10] (domyślnie: 5)");
        int lethalityLevel = Math.min(10, readInt(5));

        System.out.println("Podaj czas inkubacji wirusa (domyślnie: 2)");
        int incubationValue = readInt(2);

        System.out.println("Podaj liczbę lekarzy (domyślnie: 25)");
        int doctors = readInt(25);
        sim.addPerson(doctors, lethalityLevel, incubationValue, Doctor.class);

        System.out.println("Podaj liczbę dorosłych (domyślnie: 250)");
        int adults = readInt(250);
        sim.addPerson(adults, lethalityLevel, incubationValue, Adult.class);

        System.out.println("Podaj liczbę dzieci (domyślnie: 150)");
        int children = readInt(150);
        sim.addPerson(children, lethalityLevel, incubationValue, Child.class);

        System.out.println("Podaj liczbę starców (domyślnie: 50)");
        int elderly = readInt(50);
        sim.addPerson(elderly, lethalityLevel, incubationValue, Elder.class);

        int everyone = doctors + adults + children + elderly;
        System.out.printf("Podaj liczbę zarażonych (domyślnie: 50, max: %d)%n", everyone);
        int infected = Math.min(readInt(50), everyone);

        System.out.println("Podaj maksymalną liczbę iteracji (domyślnie: 1000, min: 1)");
        sim.setMaxIterationCount(readInt(1000));

        if (sim.getPeople().size() == 0) {
            System.out.println("Brak osób w symulacji!!!");
        }

        while (infected-- > 0) {
            Person person = sim.getPeople().get((int) (Math.random() * sim.getPeople().size()));
            if (person.isInfected()){
                infected++;
                continue;
            }
            person.setInfected(true);
        }

        sim.start();
    }

    /**
     * Sczytuje wprowadzoną wartość i zakańcza działanie programu w momencie gdy wystąpi błąd
     * @return nieujemna liczba całkowita
     */
    private static int readInt(int def) {
        try {
            String line = scanner.nextLine();
            if (line.matches("-?\\d+(\\.\\d+)?")) {
                int res = Integer.parseInt(line);
                if (res >= 0) {
                    return res;
                }
                System.out.println("Liczba nie może być ujemna! Ustawiono domyślną wartość");
                return def;
            }

            if (line.matches("^$")) {
                return def;
            }

            System.out.println("Następnym razem podaj poprawną liczbę! Ustawiono domyślną wartość");
            return def;

        } catch (InvalidParameterException | InputMismatchException | NumberFormatException e) {
            System.out.println("Następnym razem podaj poprawną liczbę! Ustawiono domyślną wartość");
            return def;
        }
    }
}

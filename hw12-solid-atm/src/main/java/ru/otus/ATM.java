package ru.otus;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM {

    List<Unit> unitList;

    private int balance;

    ATM() {
        unitList = new ArrayList<>();
        Unit unitOne = new Unit(BanknoteValue.ONE);
        Unit unitTwo = new Unit(BanknoteValue.TWO);
        Unit unitFive = new Unit(BanknoteValue.FIVE);
        Unit unitFifteen = new Unit(BanknoteValue.FIFTEEN);
        Unit unitTen = new Unit(BanknoteValue.TEN);
        Unit unitTwenty = new Unit(BanknoteValue.TWENTY);
        unitList.add(unitTwenty);
        unitList.add(unitFifteen);
        unitList.add(unitTen);
        unitList.add(unitFive);
        unitList.add(unitTwo);
        unitList.add(unitOne);
    }

    public int withdrawal(int sum) {

        if (isBalanceAvailable(sum) != -1) {
            balance -= sum;
            return sum;
        }
        System.out.println("No balance/banknote available");
        return 0;
    }

    public void deposit(int sum) {
        balance = sum;

        for (int i = 0; i < unitList.size(); i++) {
            Unit unit = unitList.get(i);
            int currentBanknoteValue = unit.getBanknoteValue().getValue();
            unit.addCount(sum / currentBanknoteValue);
            sum %= currentBanknoteValue;
        }
    }

    public int isBalanceAvailable(int sum) {

        if (balance >= sum) {

            Map<Unit, Integer> potentialWithdrawal = new HashMap<>();

            for (int i = 0; i < unitList.size(); i++) {
                Unit unit = unitList.get(i);
                int currentBanknoteValue = unit.getBanknoteValue().getValue();
                int withValueCount = sum / currentBanknoteValue;

                if (unit.getCount() >= withValueCount) {
                    potentialWithdrawal.put(unit, withValueCount);
                    sum %= currentBanknoteValue;


                } else if (unit.getCount() < withValueCount && unit.getCount() != 0) {
                    int cr = unit.getCount();
                    potentialWithdrawal.put(unit, cr);
                    sum -= cr * currentBanknoteValue;
                }
            }

            if (sum == 0) {
                for (var entry : potentialWithdrawal.entrySet()) {
                    entry.getKey().extractCount(entry.getValue());
                }
            }
        }
        return sum > 0 ? -1 : 0;
    }

    public void printBalance() {
        System.out.println("Current Balance: " + balance);
    }
}

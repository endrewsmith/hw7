package org.example.hm7;

import lombok.RequiredArgsConstructor;
import org.example.hm7.webinar.Treap;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HomeWork7Test {
    HomeWork homeWork = new HomeWork();

    // Тесты декартова дерева
    @Test
    public void TreapTest() {
        Treap<Integer> treap = new Treap<>();
        for (int i = 0; i < 100; i++) {
            treap.add(i);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(i, treap.getNodeByIndex(i).getKey());
        }
        // Проверим, что 100-ой ноды не существует
        assertThrows(NullPointerException.class, () -> {
            assertEquals(null, treap.getNodeByIndex(100).getKey());
        });

        assertEquals(99, treap.getNodeByIndex(99).getKey());
        // Удалим 99-ую ноду
        treap.removeNode(treap.getNodeByIndex(99));
        // Проверим, что 99-ой ноды нет
        assertThrows(NullPointerException.class, () -> {
            assertEquals(null, treap.getNodeByIndex(99).getKey());
        });

    }

    // Тесты для первой задачи
    @Test
    void checkFirst() {
        TestCase1 testCase = generateTestCase1();
        TestCase1 testCase2 = generateTestCase2();

        // Сначала проверим граничные значения
        // Нет дверей
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(0, testCase.actionList);
        });
        // Слишком много дверей
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(1000000001, testCase.actionList);
        });
        // Пустой список действий
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(testCase.maxDoors, null);
        });
        // Пустой список действий
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(testCase.maxDoors, new ArrayList<Action>());
        });
        // Слишком большой список действий
        List<Action> actionList = new ArrayList<>();
        for (int i = 1; i < 10000 + 2; i++) {
            actionList.add(new Action(true, 1));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(testCase.maxDoors, actionList);
        });
        // Cписок действий меньше количества дверей
        List<Action> actionList2 = new ArrayList<>();
        for (int i = 1; i < 3; i++) {
            actionList.add(new Action(true, 1));
        }
        assertThrows(IllegalArgumentException.class, () -> {
            homeWork.getOriginalDoorNumbers(testCase.maxDoors, actionList2);
        });

        assertEquals(testCase.expected, homeWork.getOriginalDoorNumbers(testCase.maxDoors, testCase.actionList));
        assertEquals(testCase2.expected, homeWork.getOriginalDoorNumbers(testCase2.maxDoors, testCase2.actionList));
    }

    private TestCase1 generateTestCase1() {
        TestCase1 testCase = new TestCase1();
        testCase.parseExpected("5\n" +
                "4\n" +
                "6\n" +
                "4\n" +
                "7");
        testCase.parseInput("20 7\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5\n" +
                "D 5\n" +
                "L 4\n" +
                "L 5");
        return testCase;
    }

    private TestCase1 generateTestCase2() {
        TestCase1 testCase = new TestCase1();
        testCase.parseExpected(
                "2\n" +
                        "4\n");
        testCase.parseInput("4 4\n" +
                "D 1\n" +
                "D 2\n" +
                "L 1\n" +
                "L 2\n");
        return testCase;
    }

    // Тесты второй задачи, в условии задачи нужно вернуть List<Integer>, а тест написан для List<String>, поэтому немного переделаем тест
    @Test
    void checkSecond() {

        // Сначала проверим граничные значения
        // Нет солдат
        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(Arrays.stream("3 1 5 2 4".split(" ")).map(Integer::parseInt).collect(Collectors.toList()),
                    homeWork.getLeaveOrder(0, 3));
        });
        // Неправильно введен интервал
        assertThrows(IllegalArgumentException.class, () -> {
            assertEquals(Arrays.stream("3 1 5 2 4".split(" ")).map(Integer::parseInt).collect(Collectors.toList()),
                    homeWork.getLeaveOrder(5, 0));
        });

        assertEquals(Arrays.stream("3 1 5 2 4".split(" ")).map(Integer::parseInt).collect(Collectors.toList()),
                homeWork.getLeaveOrder(5, 3));

    }

    @RequiredArgsConstructor
    static class TestCase1 {
        int maxDoors;
        List<Action> actionList = new ArrayList<>();
        List<Integer> expected = new ArrayList<>();

        public void parseInput(String input) {
            String[] lines = input.split("(\n|\r|\r\n)");
            maxDoors = Integer.valueOf(lines[0].split(" ")[0]);
            Arrays.stream(lines)
                    .skip(1)
                    .map(Action::parse)
                    .forEach(actionList::add);

        }


        public void parseExpected(String output) {
            String[] lines = output.split("(\n|\r|\r\n)");
            Arrays.stream(lines)
                    .map(Integer::parseInt)
                    .forEach(expected::add);
        }
    }
}

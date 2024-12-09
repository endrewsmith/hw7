package org.example;


import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1439">https://acm.timus.ru/problem.aspx?space=1&num=1439</a>
     */
    public List<Integer> getOriginalDoorNumbers(int maxDoors, List<Action> actionList) {
        // Проверим количество дверей
        if (maxDoors < 1 || maxDoors > 1000000000) {
            throw new IllegalArgumentException("Неверно введено количество дверей");
        }
        // Проверим список действий
        if (actionList == null || actionList.isEmpty() || actionList.size() > 100000) {
            throw new IllegalArgumentException("Неверно введен список действий");
        }
        // Действий не должно быть больше, чем дверей и не больше 10000
        if (actionList.size() > maxDoors || actionList.size() > 10000) {
            throw new IllegalArgumentException("Неверное количество действий");
        }
        // Создаем декартово дерево указанного размера
        Treap<Integer> treap = new Treap<>();
        for (int i = 1; i < maxDoors + 1; i++) {
            treap.add(i);
        }

        List<Integer> result = new ArrayList<>();

        for (Action action : actionList) {
            Treap.Node<Integer> nodeByIndex = treap.getNodeByIndex(action.doorNumber - 1);
            if (action.isLook) {
                result.add(nodeByIndex.getKey());
            } else {
                treap.removeNode(nodeByIndex);
            }
        }

        return result;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1521">https://acm.timus.ru/problem.aspx?space=1&num=1521</a><br/>
     * <h2>Пошагово</h2>
     * Для 5 3 входных данных:<br/>
     * _ -> 3 позиции<br/>
     * _ 1 2 <b>3</b> 4 5 => 3 <br/>
     * <b>1</b> 2 _ 4 5 => 1 <br/>
     * _ 2 4 <b>5</b> => 5 <br/>
     * <b>2</b> 4 _ => 2 <br/>
     * _ <b>4</b> => 4
     */
    public List<Integer> getLeaveOrder(int maxUnits, int leaveInterval) {
        // Проверим количество солдат
        if (maxUnits < 1) {
            throw new IllegalArgumentException("Неверно введено количество солдат");
        }
        // Проверяем интервал
        if (leaveInterval < 1) {
            throw new IllegalArgumentException("Неверно введено интервал");
        }
        // Создаем декартово дерево
        Treap<Integer> treap = new Treap<>();
        for (int i = 1; i < maxUnits + 1; i++) {
            treap.add(i);
        }
        // Список солдат по порядку выбывания из круга
        List<Integer> lineOfSoldiers = new ArrayList<>();
        int pos = 0;
        for (int i = 0; i < maxUnits - 1; i++) {
            pos = leaveInterval - pos - 1;
            Treap.Node<Integer> nodeByIndex = treap.getNodeByIndex(pos);
            lineOfSoldiers.add(nodeByIndex.getKey());
            treap.removeNode(nodeByIndex);
        }
        lineOfSoldiers.add(treap.getNodeByIndex(0).getKey());
        return lineOfSoldiers;
    }
}

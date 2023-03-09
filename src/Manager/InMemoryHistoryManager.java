package Manager;

import Task.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class InMemoryHistoryManager extends Managers implements HistoryManager {

    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    private final HashMap<Integer, Node<Task>> HistoryMap = new HashMap<>();

        @Override
        public ArrayList<Task> getHistory() {
            return customLinkedList.getTask();
        }

        public void add(Task task) {
            if(customLinkedList.size() < 10) {
                Node<Task> newNode = customLinkedList.linkLast(task);
                HistoryMap.put(task.getId(), newNode);
            } else {
                customLinkedList.remove(0);
                customLinkedList.add(task);
            }
        }

    @Override
    public void remove(int id) {
        if (HistoryMap.get(id) == null) {
            return;
        }
        customLinkedList.removeNode(HistoryMap.get(id));
        HistoryMap.remove(id);
    }

     class CustomLinkedList<T> extends LinkedList<T>{
        public Node<T> head;
        public Node<T> tail;

        private Node<T> linkLast(T data) {
                final Node<T> oldTail = tail;
                final Node<T> newNode = new Node<>(oldTail, data, null);
                tail = newNode;
                if (oldTail == null) {
                    head = newNode;
                } else {
                    oldTail.next = newNode;
                }
                return newNode;
        }

        public void removeNode(Node<T> nodeToRemove) {

            if (nodeToRemove.prev == null) {
                head = nodeToRemove.next;
            } else {
                nodeToRemove.prev.next = nodeToRemove.next;
            }

            if (nodeToRemove.next == null) {
                tail = nodeToRemove.prev;
            } else {
                nodeToRemove.next.prev = nodeToRemove.prev;
            }
        }

        public ArrayList<T> getTask() {
            ArrayList<T> taskList = new ArrayList<>();
            if (head != null) {
                taskList.add(head.data);
            } else {
                return taskList;
            }
            Node<T> current = head.next;
            while (current != null) {
                taskList.add(current.data);
                current = current.next;
            }
            return taskList;
        }
    }
}

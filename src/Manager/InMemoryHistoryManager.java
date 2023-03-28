package Manager;

import Task.*;

import java.util.ArrayList;
import java.util.HashMap;


public class InMemoryHistoryManager extends Managers implements HistoryManager {

    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    private final HashMap<Integer, Node<Task>> historyMap = new HashMap<>();

        @Override
        public ArrayList<Task> getHistory() {
            return customLinkedList.getTask();
        }

        public void add(Task task) {
                Node<Task> newNode = customLinkedList.linkLast(task);
                historyMap.put(task.getId(), newNode);
        }


    @Override
    public void remove(int id) {
        if (historyMap.get(id) == null) {
            return;
        }
        customLinkedList.removeNode(historyMap.get(id));
    }

      class CustomLinkedList<T> {
        public Node<T> head;
        private int size = 0;
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
                size++;
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
            if(historyMap.containsKey(nodeToRemove)) {
                historyMap.remove(nodeToRemove);
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

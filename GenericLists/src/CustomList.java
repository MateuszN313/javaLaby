import java.sql.Array;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CustomList<T> extends AbstractList<T> {
     static class Node<T>{
          T data;
          Node next;
          Node(T data){
               this.data = data;
               this.next = null;
          }
     }
     private Node head;
     private Node tail;
     private int size;

     public CustomList(){
          this.head = null;
          this.tail = null;
          this.size = 0;
     }
     public void addLast(T value){
          Node<T> newNode = new Node<>(value);

          if(this.head == null){
               this.head = newNode;
               this.tail = newNode;
          }else{
               this.tail.next = newNode;
               this.tail=newNode;
          }
          size++;
     }
     public T getLast(){
          if(this.tail == null) throw new IllegalStateException("List is empty");
          return (T) this.tail.data;
     }
     public void addFirst(T value){
          Node<T> newNode = new Node<>(value);

          if(this.head == null){
               this.head = newNode;
               this.tail = newNode;
          }else{
               newNode.next = this.head;
               this.head = newNode;
          }
          size++;
     }
     public T getFirst(){
          if(this.tail == null) throw new IllegalStateException("List is empty");
          return (T) this.head.data;
     }
     public T removeFirst(){
          if(this.tail == null) throw new IllegalStateException("List is empty");

          T returnValue = (T) this.head;
          this.head = this.head.next;
          size--;
          return returnValue;
     }
     public T removeLast(){
          if(this.tail == null) throw new IllegalStateException("List is empty");

          Node<T> current = this.head;
          while(current.next != this.tail){
               current = current.next;
          }

          T returnValue = (T) this.tail;
          this.tail = current;
          this.tail.next = null;
          size--;
          return returnValue;
     }

     @Override
     public String toString(){
          String result = "Lista [";

          Node<T> current = this.head;
          while(current != null){
               result += current.data;
               if(current.next != null){
                    result += ", ";
               }

               current = current.next;
          }

          result += "]";
          return result;
     }

     @Override
     public boolean add(T value){
          this.addLast(value);
          return true;
     }
     @Override
     public int size(){
          return this.size;
     }
     @Override
     public T get(int index){
          if(index < 0 || index >= this.size) throw new IndexOutOfBoundsException("Wrong index");

          Node<T> current = this.head;
          for(int i=0;i<index;i++){
               current = current.next;
          }
          return current.data;
     }

     @Override
     public Iterator<T> iterator(){
          return new Iterator<T>(){
               Node current = head;

               @Override
               public boolean hasNext() {
                    return current != null;
               }

               @Override
               public T next() {
                    if(hasNext()){
                         T data = (T) this.current.data;
                         this.current = this.current.next;
                         return data;
                    }
                    return null;
               }
          };
     }
     @Override
     public Stream<T> stream(){
          return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator(), 0), false);
     }

     public static <T> List<T> filterBySuperClass(List<T> list, Class<?> superClass){
          List<T> resultList = new ArrayList<>();
          for(T item : list){
               if(superClass.isAssignableFrom(item.getClass())){
                    resultList.add(item);
               }
          }
          return resultList;
     }
}

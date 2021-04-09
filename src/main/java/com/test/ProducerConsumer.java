package com.test;



public class ProducerConsumer {
    static class Producer extends Thread{
        Product product;
        public Producer(Product product){
            this.product = product;
        }
        @Override
        public void run(){
            for(int i =1;i <= 10;i++){
                int val = (int)(Math.random()*10);
                product.put(val);
                System.out.println("生产了"+val+"!");
                try{
                    sleep((long) (Math.random()*100));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    static class Consumer extends Thread{
        Product product;
        public Consumer(Product product){
            this.product = product;
        }
        @Override
        public void run(){
            while(true){
                int val = product.get();
                System.out.println("消费了"+val + "!");
                try {
                    sleep((long) (Math.random()*100));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        Product product = new Product();
        Producer producer = new Producer(product);
        Consumer consumer = new Consumer(product);
        producer.start();
        consumer.start();

    }


}

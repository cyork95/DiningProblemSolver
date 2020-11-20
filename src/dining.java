// Use java threads to simulate the Dining Philosophers Problem; Credits given to Prof. Riley
// Kieran Anthony and Cody York */

class dining
{
    public static void main(String args[]){
        System.out.println("Starting the Dining Philosophers Simulation\n");
        miscsubs.InitializeChecking();
        //All instances of 5 dictate the number of Philosophers present
        Chopstick[] chopsticks = new Chopstick[5];   
        for(int i=0;i<5;i++){
        	chopsticks[i]=new Chopstick(i);
        } 
        Philosopher[] philosophers = new Philosopher[5];  
        for(int i=0;i<5;i++){
        	philosophers[i]=new Philosopher(i, chopsticks);
        	philosophers[i].run();
        }
        //Have it print the times ate
        while(miscsubs.TotalEats<750){
        	for(int i=0;i<5;i++){
        		Thread t=new Thread(philosophers[i]);
        		t.start();		
        	}
        	miscsubs.RandomDelay();
        }
        miscsubs.LogResults();
    }
};

class Philosopher extends Thread {
	int philosopherIdentifier;
	Chopstick leftChopstick;
	Chopstick rightChopstick;

	public Philosopher(int number, Chopstick[] chopsticks) {
		this.philosopherIdentifier=number;
		this.leftChopstick=chopsticks[number];
		if(number==4){
			this.rightChopstick=chopsticks[0];
		} else {
			this.rightChopstick=chopsticks[number+1];
		}
	}

	public void run(){
		if(rightChopstick.inUse==false){
			if(leftChopstick.inUse==false){
				rightChopstick.grab();
				leftChopstick.grab();
				for(int i=0;i<25;i++){
					miscsubs.StartEating(philosopherIdentifier);
				}
				miscsubs.DoneEating(philosopherIdentifier);
				leftChopstick.release();
				rightChopstick.release();
			}
		}
	}
}

class Chopstick{
	boolean inUse;
	int chopstickNumber;
	
	public Chopstick(int ident){
		this.chopstickNumber=ident;
	}

	public synchronized void grab(){
		this.inUse=true;
	}
	
	public synchronized void release(){
		this.inUse=false;
	}
}


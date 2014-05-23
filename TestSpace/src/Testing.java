
public class Testing {
	public static void main(String[] args){
		for(int i = 0; i<100;i++){
			System.out.println(i);
			if(i == 50){
				break;
			}else if(i == 90){
				continue;
			}
		}
	}
}

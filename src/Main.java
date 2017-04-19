import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int temp;
        boolean mail = true;

        String uname = "estoesunaprueba123@yahoo.com";
        String pass = "prueba123";

        MailHelper mh = new MailHelper(uname,pass);

        while(mail){

            System.out.println("1 - send message \n" +
                    "2 - list messages");
            temp = in.nextInt();

            switch(temp){
                case 1:
                    mh.sendEmail("dremon@iespoblenou.org", "Hola", "Cuerpo del mensaje","");
                break;

                case 2:
                    mh.listMessages();
                break;
                default:
                    mail = false;
                break;
            }
        }
    }
}

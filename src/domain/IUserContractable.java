package domain;

public interface IUserContractable {
    interface EventListener{
        void onInput(int x, int y, int input);
        void onClick();
    }
    interface View{
        void setListener(EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(Game game);
        void showDialog(String message);
        void showError(String message);
    }
}

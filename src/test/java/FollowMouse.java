import org.kakara.engine.GameHandler;
import org.kakara.engine.components.Component;
import org.kakara.engine.math.Vector2;
import org.kakara.engine.math.Vector3;

public class FollowMouse extends Component {

    @Override
    public void start() {

    }

    @Override
    public void update() {
//        System.out.println(GameHandler.getInstance().getMouseInput().getPosition());
//        getTransform().setPosition(new Vector3(new Vector2(GameHandler.getInstance().getMouseInput().getPosition()), 0));
    }
}
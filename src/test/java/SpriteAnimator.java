import org.kakara.engine.components.Component;
import org.kakara.engine.utils.Time;

public class SpriteAnimator extends Component {

    private float timer = 0;

    @Override
    public void start() {

    }

    @Override
    public void update() {
        timer += Time.getDeltaTime();
        if (timer > 0.2) {
            timer = 0;
            if (getGameItem().getTextPos() < 11)
                getGameItem().setTextPos(getGameItem().getTextPos() + 1);
            else {
                getGameItem().setTextPos(0);
            }
        }
    }
}

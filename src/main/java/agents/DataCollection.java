package agents;

import burlap.behavior.policy.Policy;
import burlap.behavior.policy.PolicyUtils;
import burlap.behavior.policy.RandomPolicy;
import burlap.behavior.singleagent.Episode;
import burlap.mdp.singleagent.SADomain;
import burlap.shell.visual.VisualExplorer;
import edu.brown.cs.burlap.ALEDomainGenerator;
import edu.brown.cs.burlap.ALEEnvironment;
import edu.brown.cs.burlap.ALEState;
import edu.brown.cs.burlap.gui.ALEVisualExplorer;
import edu.brown.cs.burlap.gui.ALEVisualizer;
import game_objects.*;
import io.OpenCVIO;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by maroderi on 8/2/16.
 */
public class DataCollection {

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("Usage: <path/to/ale/executable> <path/to/rom/file> <path/to/write/data>");
        }
        String alePath = args[0];
        String romPath = args[1];
        String dataPath = args[2];

        ALEDomainGenerator domGen = new ALEDomainGenerator();
        SADomain domain = domGen.generateDomain();
        ALEEnvironment env = new ALEEnvironment(alePath, romPath, 4);
        ALEVisualExplorer exp = new ALEVisualExplorer(domain, env, ALEVisualizer.create());
        exp.initGUI();
        exp.startLiveStatePolling(1000/60);

        Policy p = new RandomPolicy(domain);

        PrintStream out;
        try {
            String indexFile = Paths.get(dataPath, "index").toString();
            out = new PrintStream(new BufferedOutputStream(new FileOutputStream(indexFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        out.printf("paddle_x,paddle_y,ball_x,ball_y,enemy_paddle_x,enemy_paddle_y," +
                "action_name,reward,is_terminal,screen_image\n");


        List<SpriteTemplate> templates = ObjectConstants.PONG_SPRITE_TEMPLATES;
        for (int i = 0; i < 100000; i++) {
            Episode ep = PolicyUtils.rollout(p, env, 1);
            ALEState aleState = (ALEState) ep.state(1);

            String imageName = String.format("screen_%d.png",i);
            OpenCVIO.saveMat(aleState.getScreen(), Paths.get(dataPath, imageName).toString());

            List<Sprite> sprites = SpriteFinder.findSprites(aleState, templates);
            Sprite paddle = null, ball = null, enemyPaddle = null;
            for (Sprite sprite : sprites) {
                if (sprite.type.equals(ObjectConstants.PONG_PADDLE_TYPE)) {
                    paddle = sprite;
                } else if (sprite.type.equals(ObjectConstants.PONG_BALL_TYPE)) {
                    ball = sprite;
                } else if (sprite.type.equals(ObjectConstants.PONG_ENEMY_PADDLE_TYPE)) {
                    enemyPaddle = sprite;
                }
            }

            if (paddle != null) {
                out.printf("%d,%d,", paddle.boundingRect.x(), paddle.boundingRect.y());
            } else {
                out.printf("-1,-1,");
            }

            if (ball != null) {
                out.printf("%d,%d,", ball.boundingRect.x(), ball.boundingRect.y());
            } else {
                out.printf("-1,-1,");
            }

            if (enemyPaddle != null) {
                out.printf("%d,%d,", enemyPaddle.boundingRect.x(), enemyPaddle.boundingRect.y());
            } else {
                out.printf("-1,-1,");
            }

            out.printf("%s,%f,%b,%s\n",
                    ep.action(0).actionName(), ep.reward(1), env.isInTerminalState(),
                    imageName);

            if (env.isInTerminalState()) {
                env.resetEnvironment();
            }
        }
    }
}

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
import game_objects.ObjectConstants;
import game_objects.ObjectVisualizer;

/**
 * Created by maroderi on 7/20/16.
 */
public class RandomAgent {
    public static void main(String[] args) {
        String alePath = "/home/maroderi/projects/Arcade-Learning-Environment/ale";
        String romPath = "/home/maroderi/projects/atari_roms/pong.bin";

        // Create the ALE domain
        ALEDomainGenerator domGen = new ALEDomainGenerator();
        SADomain domain = domGen.generateDomain();

        // Create the environment
        ALEEnvironment env = new ALEEnvironment(alePath, romPath, 4);

        // Create and initialize the visualizer
        ALEVisualExplorer exp = new ALEVisualExplorer(domain, env, ALEVisualizer.create());
        exp.initGUI();

        // Refresh the visualizer at 60Hz
        exp.startLiveStatePolling(1000/60);

        // Initialize the policy
        Policy p = new RandomPolicy(domain);

        VisualExplorer objectVis = new VisualExplorer(domain, env, ObjectVisualizer.create(ObjectConstants.PONG_SPRITE_TEMPLATES));
        objectVis.initGUI();

        // Run the policy indefinitely
        for (int i = 0; i < 10000; i++) {
            if (env.isInTerminalState()) {
                env.resetEnvironment();
            }

            Episode ep = PolicyUtils.rollout(p, env, 1);

            ALEState aleState = (ALEState) env.currentObservation();
            objectVis.updateState(aleState);
        }
    }
}

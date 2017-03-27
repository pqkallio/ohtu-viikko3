/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtuesimerkki;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pqkallio
 */
public class StatisticsTest {
    private Statistics stats;
    public StatisticsTest() {
    }
    
    @Before
    public void setUp() {
        Reader reader = new ReaderImpl();
        this.stats = new Statistics(reader);
    }

    @Test
    public void testSearchFound() {
        Player p = this.stats.search("PIT Ed");
        assertNotNull(p);
    }
    
    @Test
    public void testSearchNotFound() {
        Player p = this.stats.search("PHI Ed");
        assertNull(p);
    }

    @Test
    public void testTeamWithPlayers() {
        List<Player> team = this.stats.team("ANA");
        assertEquals(4, team.size());
    }
    
    @Test
    public void testTeamWithoutPlayers() {
        List<Player> team = this.stats.team("PHI");
        assertEquals(0, team.size());
    }
    
    @Test
    public void testTeamNotFound() {
        List<Player> team = this.stats.team("butt");
        assertEquals(0, team.size());
    }

    @Test
    public void testTopScorersSeven() {
        List<Player> scorers = this.stats.topScorers(7);
        assertEquals(7, scorers.size());
    }
    
    @Test
    public void testTopScorersSix() {
        List<Player> scorers = this.stats.topScorers(6);
        assertEquals(6, scorers.size());
    }
    
    @Test
    public void testTopScorersFour() {
        List<Player> scorers = this.stats.topScorers(4);
        assertEquals(4, scorers.size());
    }
    
    @Test
    public void testTopScorersTwo() {
        List<Player> scorers = this.stats.topScorers(2);
        assertEquals(2, scorers.size());
    }
    
    @Test
    public void testTopScorersZero() {
        List<Player> scorers = this.stats.topScorers(0);
        assertEquals(0, scorers.size());
    }
    
    @Test
    public void testTopScorersMinusOne() {
        List<Player> scorers = this.stats.topScorers(-1);
        assertEquals(0, scorers.size());
    }
    
    private class ReaderImpl implements Reader {
        private final List<Player> players;
        private final String[] teams = {"PHI", "ANA", "DAL", "PIT"};
        private final String[] names = {"Ed", "Edd", "Eddy", "Eddie"};

        public ReaderImpl() {
            this.players = createPlayers();
        }
        
        @Override
        public List getPlayers() {
            return this.players;
        }    

        private List<Player> createPlayers() {
            List<Player> ps = new ArrayList<>();
            for (int i = 1; i < teams.length; i++) {
                for (int j = 0; j < names.length; j++) {
                    String name = teams[i] + " " + names[j];
                    Player p = new Player(name, teams[i], j, i);
                    ps.add(p);
                }
            }
            return ps;
        }
    }
    
    private class ReaderImplEmpty implements Reader {

        @Override
        public List getPlayers() {
            return new ArrayList<>();
        }
        
    }
}

package org.jxsl13.takethematch.main;

/**
 * Created by John on 26.04.2014.
 */
public class TakeTheMatchEngine {
    private boolean gameRuns;
    private int matchesLimiter;
    private int matches;
    private int maxMatchesToDraw;
    private double randomNumber;
    private int playerMatchesDrawn;
    private boolean playerTurn;
    private int aiPlayerMatchesDrawn;
    private boolean aiPlayerTurn;
    public boolean invertedStart;
    private int inverseGameplay;
    private int aiHardness;

    public TakeTheMatchEngine(int MaxMatchesToDraw, int MatchesLimiter, boolean playerStart, boolean InverseGamePlay, int AiHardness, boolean useRandomizerFactor) {
        gameRuns = true;
        if (MatchesLimiter > 0) {
            matchesLimiter = MatchesLimiter;
        } else {
            matchesLimiter = 100;
        }
        if (MaxMatchesToDraw > 0) {
            maxMatchesToDraw = MaxMatchesToDraw;
        } else {
            maxMatchesToDraw = 3;
        }

        if (useRandomizerFactor) {
            while (matches < matchesLimiter / 2) {
                randomNumber = Math.random();
                matches = (int) (MatchesLimiter * randomNumber);
            }
        } else {
            randomNumber = 0;
            matches = MatchesLimiter;
        }

        if (AiHardness <= 3 && AiHardness >= 1) {
            aiHardness = AiHardness;
        } else {
            aiHardness = 3;
        }
        if (InverseGamePlay) {
            inverseGameplay = 1;
        } else {
            inverseGameplay = 0;
        }

        playerMatchesDrawn = 0;

        aiPlayerMatchesDrawn = 0;
        if (playerStart) {
            playerTurn = true;
            aiPlayerTurn = false;
            invertedStart = false;
        } else {
            playerTurn = false;
            aiPlayerTurn = true;
            invertedStart = true;
        }

    }

    public void RestartGame(int MaxMatchesToDraw, int MatchesLimiter, boolean playerStart, boolean InverseGamePlay, int AiHardness, boolean useRandomizerFactor) {
        gameRuns = true;
        if (MatchesLimiter > 0) {
            matchesLimiter = MatchesLimiter;
        } else {
            matchesLimiter = 100;
        }
        if (MaxMatchesToDraw > 0) {
            maxMatchesToDraw = MaxMatchesToDraw;
        } else {
            maxMatchesToDraw = 3;
        }

        if (useRandomizerFactor) {
            while (matches < matchesLimiter / 2) {
                randomNumber = Math.random();
                matches = (int) (MatchesLimiter * randomNumber);
            }
        } else {
            randomNumber = -1;
            matches = MatchesLimiter;
        }
        if (AiHardness <= 3 && AiHardness >= 1) {
            aiHardness = AiHardness;
        } else {
            aiHardness = 3;
        }
        if (InverseGamePlay) {
            inverseGameplay = 1;
        } else {
            inverseGameplay = 0;
        }

        playerMatchesDrawn = 0;

        aiPlayerMatchesDrawn = 0;
        if (playerStart) {
            playerTurn = true;
            aiPlayerTurn = false;
            invertedStart = false;
        } else {
            playerTurn = false;
            aiPlayerTurn = true;
            invertedStart = true;
        }

    }

    public boolean getGameRunStatus() {
        return gameRuns;
    }

    public int getMatchesLeft() {
        return matches;
    }

    public int getMaxMatchesToDraw() {
        return maxMatchesToDraw;
    }

    /**
     * @param MatchesToDraw
     */
    public void play(int MatchesToDraw) {
        if (checkMatchesToDraw(MatchesToDraw)) {

            if (playerTurn) {
                matches = matches - MatchesToDraw;
                playerMatchesDrawn = MatchesToDraw;
                playerTurn = false;
                aiPlayerTurn = true;
                updateGameRunStatus();

            }
            if (aiPlayerTurn) {
                int aiDraw = playAI();
                if (checkMatchesToDraw(aiDraw)) {
                    matches = matches - aiDraw;
                    aiPlayerMatchesDrawn = aiDraw;
                    aiPlayerTurn = false;
                    playerTurn = true;
                    updateGameRunStatus();
                } else {
                    aiPlayerMatchesDrawn = 0;
                }

            }

        }

    }

    public void playInverseStart() {
        if (aiPlayerTurn) {
            int aiDraw = playAI();
            if (checkMatchesToDraw(aiDraw)) {
                matches = matches - aiDraw;
                aiPlayerMatchesDrawn = aiDraw;
                aiPlayerTurn = false;
                playerTurn = true;
                updateGameRunStatus();
            } else {
                aiPlayerMatchesDrawn = 0;
            }

        }
    }

    public boolean getInvertedStart() {

        return invertedStart;

    }

    public int getAiPlayerMatchesDrawn() {
        return aiPlayerMatchesDrawn;
    }

    public int getPlayerMatchesDrawn() {
        return playerMatchesDrawn;
    }

    public boolean getPlayerWonTheGame() {
        if (playerTurn == false && matches == 0) {
            return true;
        } else {
            return false;
        }
    }

    private int playAI() {
        int matchestodraw = 0;
        for (int i = 1; i <= maxMatchesToDraw; i++) {
            if (aiHardness >= 1) {
                if (matches == 0) {
                    matchestodraw = 0;
                    break;
                }

                if (matches - i == 0) {
                    matchestodraw = i - inverseGameplay;
                    break;
                }
            }

            if (aiHardness >= 2) {

                if ((matches % (maxMatchesToDraw + 1)) == 0) {
                    matchestodraw = maxMatchesToDraw;
                    break;
                }
            }
            if (aiHardness >= 3) {

                if ((matches - i) % (maxMatchesToDraw + 1) == 0) {
                    matchestodraw = i;
                    break;
                }
            }

        }

        if (checkMatchesToDraw(matchestodraw)) {
            return matchestodraw;

        } else {
            matchestodraw = (int) (Math.random() * maxMatchesToDraw);
            return matchestodraw;
        }

    }

    private void updateGameRunStatus() {
        if (matches > 0) {
            gameRuns = true;
        } else {
            gameRuns = false;
        }

    }

    public boolean checkMatchesToDraw(int MatchesToDraw) {
        if (MatchesToDraw > 0 && MatchesToDraw <= maxMatchesToDraw && matches - MatchesToDraw >= 0) {
            return true;
        } else {
            return false;
        }
    }


    public String getDebugginInfo() {
        return  gameRuns + "\n" +
                matchesLimiter + "\n" +
                matches + "\n" +
                maxMatchesToDraw + "\n" +
                randomNumber + "\n" +
                playerMatchesDrawn + "\n" +
                playerTurn + "\n" +
                aiPlayerMatchesDrawn + "\n" +
                aiPlayerTurn + "\n" +
                invertedStart + "\n" +
                inverseGameplay + "\n" +
                aiHardness;
    }

}

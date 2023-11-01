# Dchallenges

Welcome to the Minecraft Mini Challenges Plugin repository! This plugin allows server administrators to initiate mini challenges for players. Participants compete against one another to complete tasks like "find a diamond" as fast as possible. The plugin tracks and announces winners and maintains a leaderboard.

### Server Installation

Download the latest release from CurseForge or something similar (coming soon).

1. Clone this repository into a new directory and then `cd` into it.
2. Make sure you have JDK 21 and Maven installed.
3. Run `mvn clean install` to build the plugin.
4. Copy the generated .jar file from the `dchallenges/target` directory into your Minecraft server's `plugins/` directory on a SpigotMC Server.
5. Restart your server.

### Usage

- Starting a challenge game: `/startchallengegame`
- Ending a challenge game: `/endchallengegame`
- Viewing the leaderboard: `/lb`
- Setting the challenge end location on your current player location: `/sethub`
- Skipping the grace period between challenges: `/skipgp`

### Contributing

1. Fork the repository.
2. Clone your forked repository.
3. Make your changes and improvements.
4. Push the changes to your fork. Please be sure to include thourough documentation and tests.
5. Open a pull request.

If you have an idea for a challenge that should be implemented, describe it in great detail in a GitHub issue and label it with the challenge label.

### Support

For issues, bugs, and feature requests, open a GitHub issue. For direct assistance, contact the repository owner.
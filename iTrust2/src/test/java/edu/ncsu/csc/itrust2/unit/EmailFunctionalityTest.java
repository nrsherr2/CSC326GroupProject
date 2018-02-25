package edu.ncsu.csc.itrust2.unit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.mail.MessagingException;

import org.junit.Assert;
import org.junit.Test;

import edu.ncsu.csc.itrust2.utils.EmailUtil;

/**
 * Tests basic email functionality by sending one of a collection of emails
 *
 * @author Nick Sherrill (nrsherr2)
 *
 */
public class EmailFunctionalityTest {

    /**
     * Tests sending the email we will revisit this later.
     */

    @Test
    public void testSendEmail () {
        final String destination = "csc326.gps2018.g4@gmail.com";
        final String subject = "test " + new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.SSS" ).format( new Date() );
        final String body = getMessage();
        try {
            EmailUtil.sendEmail( destination, subject, body );
            System.out.println(
                    "If you are in the GP-201-4 group or have access to their throwaway email, check that email's inbox to see if they got that message." );
            System.out.println(
                    "If you are a group that came after us, change the destination field to whatever email you want to send it to" );
        }
        catch ( final MessagingException e ) {
            Assert.fail( "Something went wrong when sending email" + e.getMessage() );
        }
    }

    /**
     * Interface that allows multiple methods to be kept in an array
     *
     * @author Nick
     *
     */
    interface MessageProvider {
        /**
         * The function that I want to handle with this interface
         *
         * @return the string that this interface keeps
         */
        String getString ();
    }

    private String navySeal () {
        return "What the fk did you just fking say about me, you little bitch? Iâ€™ll have you know I graduated top of"
                + " my class in the Navy Seals, and Iâ€™ve been involved in numerous secret raids on Al-Quaeda, and I have"
                + " over 300 confirmed kills. I am trained in gorilla warfare and Iâ€™m the top sniper in the entire US "
                + "armed forces. You are nothing to me but just another target. I will wipe you the fk out with "
                + "precision the likes of which has never been seen before on this Earth, mark my fking words. You "
                + "think you can get away with saying that sh to me over the Internet? Think again, fker. As we "
                + "speak I am contacting my secret network of spies across the USA and your IP is being traced right "
                + "now so you better prepare for the storm, maggot. The storm that wipes out the pathetic little thing "
                + "you call your life. Youâ€™re fking dead, kid. I can be anywhere, anytime, and I can kill you in over"
                + " seven hundred ways, and thatâ€™s just with my bare hands. Not only am I extensively trained in "
                + "unarmed combat, but I have access to the entire arsenal of the United States Marine Corps and I will "
                + "use it to its full extent to wipe your miserable ass off the face of the continent, you little sh. "
                + "If only you could have known what unholy retribution your little â€œcleverâ€� comment was about to bring "
                + "down upon you, maybe you would have held your fking tongue. But you couldnâ€™t, you didnâ€™t, and now "
                + "youâ€™re paying the price, you goddamn idiot. I will sh fury all over you and you will drown in it. "
                + "Youâ€™re fking dead, kiddo.";
    }

    private String darthPlagueis () {
        return "Did you ever hear the tragedy of Darth Plagueis The Wise? I thought not. "
                + "Itâ€™s not a story the Jedi would tell you. Itâ€™s a Sith legend. "
                + "\nDarth Plagueis was a Dark Lord of the Sith, so powerful and so wise he could use the Force to "
                + "influence the midichlorians to create lifeâ€¦ He had such a knowledge of the dark side that he could "
                + "even keep the ones he cared about from dying. The dark side of the Force is a pathway to many "
                + "abilities some consider to be unnatural. "
                + "\nHe became so powerfulâ€¦ the only thing he was afraid of was losing his power, which eventually, "
                + "of course, he did. Unfortunately, he taught his apprentice everything he knew, then his "
                + "apprentice killed him in his sleep. "
                + "\nIronic. He could save others from death, but not himself.";
    }

    private String wednesdayFrog () {
        return "It is Wednesday, my dudes.";
    }

    private String beeMovieIntro () {
        return "According to all known laws\r\n" + "of aviation,\r\n" + "\r\n" + "  \r\n" + "there is no way a bee\r\n"
                + "should be able to fly.\r\n" + "\r\n" + "  \r\n" + "Its wings are too small to get\r\n"
                + "its fat little body off the ground.\r\n" + "\r\n" + "  \r\n" + "The bee, of course, flies anyway\r\n"
                + "\r\n" + "  \r\n" + "because bees don't care\r\n" + "what humans think is impossible.";
    }

    private String rickRoll () {
        return "We're no strangers to love\r\n" + "You know the rules and so do I\r\n"
                + "A full commitment's what I'm thinking of\r\n" + "You wouldn't get this from any other guy\r\n"
                + "\r\n" + "I just wanna tell you how I'm feeling\r\n" + "Gotta make you understand\r\n" + "\r\n"
                + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry\r\n"
                + "Never gonna say goodbye\r\n" + "Never gonna tell a lie and hurt you\r\n" + "\r\n"
                + "We've known each other for so long\r\n" + "Your heart's been aching but you're too shy to say it\r\n"
                + "Inside we both know what's been going on\r\n" + "We know the game and we're gonna play it\r\n"
                + "\r\n" + "And if you ask me how I'm feeling\r\n" + "Don't tell me you're too blind to see\r\n"
                + "\r\n" + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry\r\n"
                + "Never gonna say goodbye\r\n" + "Never gonna tell a lie and hurt you\r\n"
                + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry\r\n"
                + "Never gonna say goodbye\r\n" + "Never gonna tell a lie and hurt you\r\n" + "\r\n"
                + "Never gonna give, never gonna give\r\n" + "(Give you up)\r\n"
                + "(Ooh) Never gonna give, never gonna give\r\n" + "(Give you up)\r\n" + "\r\n"
                + "We've known each other for so long\r\n" + "Your heart's been aching but you're too shy to say it\r\n"
                + "Inside we both know what's been going on\r\n" + "We know the game and we're gonna play it\r\n"
                + "\r\n" + "I just wanna tell you how I'm feeling\r\n" + "Gotta make you understand\r\n" + "\r\n"
                + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry\r\n"
                + "Never gonna say goodbye\r\n" + "Never gonna tell a lie and hurt you\r\n"
                + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry\r\n"
                + "Never gonna say goodbye\r\n" + "Never gonna tell a lie and hurt you\r\n"
                + "Never gonna give you up\r\n" + "Never gonna let you down\r\n"
                + "Never gonna run around and desert you\r\n" + "Never gonna make you cry";
    }

    private String fitnessGram () {
        return "The FitnessGram Pacer Test is a multistage aerobic capacity test that "
                + "progressively gets more difficult as it continues. The 20 meter pacer test will begin in 30 seconds."
                + " Line up at the start. The running speed starts slowly but gets faster each minute after you hear"
                + " this signal bodeboop. A sing lap should be completed every time you hear this sound. ding "
                + "Remember to run in a straight line and run as long as possible. The second time you fail to "
                + "complete a lap before the sound, your test is over. The test will begin on the word start. On your "
                + "mark. Get ready!â€¦ Start. dingï»¿";
    }

    private String thicc () {
        return "In japan we don't say 'I love you' we say ä¹‡ä¹‚ã„’å°ºå�‚ ã„’å�„ä¸¨åŒšåŒš which doesn't mean anything it "
                + "just spells out 'Extra Thicc' in japanese symbols.";
    }

    private String rickAndMorty () {
        return "To be fair, you have to have a very high IQ to understand Rick and Morty. The humour is extremely "
                + "subtle, and without a solid grasp of theoretical physics most of the jokes will go over a typical "
                + "viewerâ€™s head. Thereâ€™s also Rickâ€™s nihilistic outlook, which is deftly woven into his "
                + "characterisation- his personal philosophy draws heavily from Narodnaya Volya literature, for "
                + "instance. The fans understand this stuff; they have the intellectual capacity to truly appreciate "
                + "the depths of these jokes, to realise that theyâ€™re not just funny- they say something deep about "
                + "LIFE. As a consequence people who dislike Rick & Morty truly ARE idiots- of course they wouldnâ€™t "
                + "appreciate, for instance, the humour in Rickâ€™s existential catchphrase â€œWubba Lubba Dub Dub,â€� "
                + "which itself is a cryptic reference to Turgenevâ€™s Russian epic Fathers and Sons. Iâ€™m smirking "
                + "right now just imagining one of those addlepated simpletons scratching their heads in confusion "
                + "as Dan Harmonâ€™s genius wit unfolds itself on their television screens. What fools.. how I pity "
                + "them. ðŸ˜‚\r\n" + "\r\n" + "And yes, by the way, i DO have a Rick & Morty tattoo. "
                + "And no, you cannot see it. Itâ€™s for the ladiesâ€™ eyes only- and even then they have to"
                + " demonstrate that theyâ€™re within 5 IQ points of my own (preferably lower) beforehand. "
                + "Nothin personnel kid ðŸ˜Ž";
    }

    private String mesothelioma () {
        return "â€œIf you or a loved one has been diagnosed with Mesothelioma you may to be entitled to financial "
                + "compensation. Mesothelioma is a rare cancer linked to asbestos exposure. Exposure to asbestos "
                + "in the Navy, shipyards, mills, heating, construction or the automotive industries may put you at "
                + "risk. Please donâ€™t wait, call 1-800-99 LAW USA today for a free legal consultation and financial "
                + "information packet. Mesothelioma patients call now! 1-800-99 LAW USAâ€�";
    }

    private String thinking () {
        return "â €â °â¡¿â ¿â ›â ›â »â ¿â£·\r\n" + "â €â €â €â €â €â €â£€â£„â¡€â €â €â €â €â¢€â£€â£€â£¤â£„â£€â¡€\r\n"
                + "â €â €â €â €â €â¢¸â£¿â£¿â£·â €â €â €â €â ›â ›â£¿â£¿â£¿â¡›â ¿â ·\r\n"
                + "â €â €â €â €â €â ˜â ¿â ¿â ‹â €â €â €â €â €â €â£¿â£¿â£¿â ‡\r\n"
                + "â €â €â €â €â €â €â €â €â €â €â €â €â €â €â €â ˆâ ‰â �\r\n" + "\r\n"
                + "â €â €â €â €â£¿â£·â£„â €â¢¶â£¶â£·â£¶â£¶â£¤â£€\r\n"
                + "â €â €â €â €â£¿â£¿â£¿â €â €â €â €â €â ˆâ ™â »â —\r\n"
                + "â €â €â €â£°â£¿â£¿â£¿â €â €â €â €â¢€â£€â£ â£¤â£´â£¶â¡„\r\n"
                + "â €â£ â£¾â£¿â£¿â£¿â£¥â£¶â£¶â£¿â£¿â£¿â£¿â£¿â ¿â ¿â ›â ƒ\r\n"
                + "â¢°â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â¡„\r\n" + "â¢¸â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â¡�\r\n"
                + "â ˆâ¢¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â£¿â �\r\n" + "â €â €â ›â¢¿â£¿â£¿â£¿â£¿â£¿â£¿â¡¿â Ÿ\r\n"
                + "â €â €â €â €â €â ‰â ‰â ‰\r\n" + "";
    }

    private String ea () {
        return "The intent is to provide players with a sense of pride and accomplishment for unlocking"
                + " different heroes.\r\n" + "\r\n"
                + "As for cost, we selected initial values based upon data from the Open Beta and other "
                + "adjustments made to milestone rewards before launch. Among other things, we're looking "
                + "at average per-player credit earn rates on a daily basis, and we'll be making constant "
                + "adjustments to ensure that players have challenges that are compelling, rewarding, and of "
                + "course attainable via gameplay.\r\n" + "\r\n"
                + "We appreciate the candid feedback, and the passion the community has put forth around the "
                + "current topics here on Reddit, our forums and across numerous social media outlets.\r\n" + "\r\n"
                + "Our team will continue to make changes and monitor community feedback and update everyone "
                + "as soon and as often as we can.\r\n" + "";
    }

    private String behindTheMeme () {
        return "Number 15:Burger King Foot Lettuce\r\n" + "\r\n"
                + "The last thing you'd want in your Burger King burger is someone's foot fungus. "
                + "But as it turns out,that might be what you get. "
                + "A 4channer uploaded a photo to the site anonymously to the site showcasing his feet in a plastic "
                + "bin of lettuce with the statement \"This is the lettuce you eat at Burger King\". "
                + "Admittedly he had " + "shoes on but, " + "that's even worse. "
                + "The post went live at 11:38 PM on July 16 and a mere 20 minutes later, "
                + "the Burger King in question was alerted to the rogue employee...at least, "
                + "I hope he is rogue.How did it happen? "
                + "Well the BK employee hadn't removed the Exif data from the uploaded photo, "
                + "which suggested the culprit was somewhere in Mayfield Heights,Ohio.This was at 11:47 "
                + "PM 3 minutes later, " + "at 11:50,the Burger King branch adress was posted, "
                + "with wishes of happy unemployment.5 minutes later, "
                + "the news station was contacted by another 4channer. " + "And 3 minutes later, " + "at 11:58, "
                + "a link was posted,BK's \"Tell us about us\" online forum. "
                + "The foot photo,otherwise known as Exhibit A was attached. "
                + "Cleveland Scene Magazine contacted the BK in question the next day.When questioned, "
                + "the breakfast shift manager said \"Oh, " + "I know who that is.He is getting fired. "
                + "\"Mystery solved by 4chan. " + "Now we can go back to eating our fast food in peace\r\n" + "";
    }

    private String getMessage () {
        final Random r = new Random( System.currentTimeMillis() );
        final MessageProvider[] providers = new MessageProvider[] { new MessageProvider() {
            @Override
            public String getString () {
                return navySeal();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return darthPlagueis();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return wednesdayFrog();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return beeMovieIntro();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return rickRoll();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return fitnessGram();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return thicc();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return rickAndMorty();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return mesothelioma();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return thinking();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return ea();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return behindTheMeme();
            }
        }, new MessageProvider() {
            @Override
            public String getString () {
                return "ðŸ‘‰ðŸ˜ŽðŸ‘‰ zoop";
            }
        }

        };
        final int messageNum = r.nextInt( providers.length );
        return providers[messageNum].getString();
    }
}

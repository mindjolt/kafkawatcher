import java.util
import java.util.Properties
import scala.collection.JavaConversions._
import org.apache.kafka.clients.consumer._

import cs.ticket.scala.CSTicket
import java.io._
import scala.sys.process._

//Consumer Object
object consumer1 extends App{

  //Main file
 // def main(args: Array[String]){

    //Data references
    val read_topic="cs.ticket"
    val read_add="10.5.99.82:9092"
    val write_topic="topic1"
    val write_add="localhost:9092"
  var _cgrp =System.currentTimeMillis().toString
  var _hour = 12

  if (args.length != 0) {
    _hour =args(0).toInt
    if (args.length > 1) {
      _cgrp = args(1)
    }  }

  val cnsmr_hours= _hour
  val cnsmr_grp= _cgrp

    //Calling consumer client
    playconsumer(read_topic,read_add,cnsmr_grp,cnsmr_hours);

 // }

  //Function to resolve the output from CSticket
  def show(x: Option[Any]) = x match {
      case Some(s) => s.toString()
      case None => "N/A"
  }

  //Function to convert arrayBytes to Hex form
  def bytes2hex(bytes: Array[Byte]): String = {
       bytes.map("%02x".format(_)).mkString
  }


  //Fucntion to play consumer
  def playconsumer(read_topic:String,read_add:String, cnsmr_grp:String, cnsmr_hours:Int ): Int = {





    //Data
    val currenttime = System.currentTimeMillis()
    val offset24hr = currenttime -(1000 * cnsmr_hours * 3600 )

    val consumer_group = cnsmr_grp
    //
    val consumer_props = new Properties()
    consumer_props.put("bootstrap.servers",read_add)
    consumer_props.put("group.id",consumer_group)
    consumer_props.put("auto.offset.reset", "earliest")
    consumer_props.put("key.deserializer",  "org.apache.kafka.common.serialization.ByteArrayDeserializer")
    consumer_props.put("value.deserializer","org.apache.kafka.common.serialization.ByteArrayDeserializer")

    val kfk_consumer = new KafkaConsumer[Array[Byte],Array[Byte]](consumer_props)
    kfk_consumer.subscribe(util.Collections.singletonList(read_topic))
  //  println("Execution started here")


    val f = new File("output6.txt")
   val write=new PrintWriter(f)
   write.write(s"$consumer_group and and and $currenttime and $offset24hr  and $cnsmr_hours and \n")

   while(true) {
     val records = kfk_consumer.poll(100)

     for (record <- records.iterator()) {


       // println(record.value().mkString(" "))


       val valueBytes = record.value()


       val vbcv = CSTicket(valueBytes)


       //println(vbcv.getClass.getDeclaredFields.mkString(" "))
       // println(vbcv)

       val keyeq = show(vbcv.ticketId)

       val gameeq = show(vbcv.game)
       val ticketTypeeq = show(vbcv.ticketType)
       val timestampeq = show(vbcv.timestamp)
       val statuseq = show(vbcv.status)
       val commenteq = show(vbcv.comment)
       val playerNameeq = show(vbcv.playerName)
       val playerEmaileq = show(vbcv.playerEmail)
       val subjecteq = show(vbcv.subject)
       val languageeq = show(vbcv.language)
       val issueTypeeq = show(vbcv.issueType)
       val clientIdeq = show(vbcv.clientId)
       val gameVersioneq = show(vbcv.gameVersion)
       val deviceNameeq = show(vbcv.deviceName)
       val platformeq = show(vbcv.platform)
       val oseq = show(vbcv.os)
       val starRatingeq = show(vbcv.starRating)
       val countryeq = show(vbcv.country)
       val appStoreeq = show(vbcv.appStore)
       // val valeq=s"$gameeq|$keyeq|$ticketTypeeq|$timestampeq|$statuseq|$commenteq|$playerNameeq|$playerEmaileq|$subjecteq|$languageeq|$issueTypeeq|$clientIdeq|$gameVersioneq|$deviceNameeq|$platformeq|$oseq|$starRatingeq|$countryeq|$appStoreeq\n"



       if (timestampeq.toLong > offset24hr) {

       val valeq = s"$keyeq|$gameeq|True|True|$statuseq|$ticketTypeeq|$timestampeq|$playerNameeq|$playerEmaileq|(Comment_Disabled)|(Subject_currently_disabled)|$languageeq|$issueTypeeq|$clientIdeq|$gameVersioneq|$deviceNameeq|$platformeq|$oseq|$countryeq|$starRatingeq\n"
       println(valeq)

       write.write(valeq)
     }



     }

   }
    return 0
  }









}
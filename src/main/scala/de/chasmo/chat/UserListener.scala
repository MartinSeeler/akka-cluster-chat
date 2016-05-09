package de.chasmo.chat

import akka.actor._
import akka.cluster.{Cluster, MemberStatus}
import akka.cluster.ClusterEvent.{CurrentClusterState, MemberEvent, MemberRemoved, MemberUp}

class UserListener extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  override def preStart(): Unit =
    cluster.subscribe(self, classOf[MemberEvent])

  override def postStop(): Unit =
    cluster unsubscribe self

  var nodes = Set.empty[Address]

  def receive = {
    case state: CurrentClusterState =>
      nodes = state.members.collect {
        case m if m.status == MemberStatus.Up => m.address
      }
    case MemberUp(member) =>
      nodes += member.address
      log.info("Member is Up: {}. {} nodes in cluster",
        member.address, nodes.size)
    case MemberRemoved(member, _) =>
      nodes -= member.address
      log.info("Member is Removed: {}. {} nodes cluster",
        member.address, nodes.size)
    case _: MemberEvent => // ignore
  }

}

object UserListener {

  def props: Props = Props(classOf[UserListener])

}

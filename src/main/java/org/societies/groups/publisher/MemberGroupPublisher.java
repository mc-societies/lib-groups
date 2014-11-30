package org.societies.groups.publisher;

import com.google.common.util.concurrent.ListenableFuture;
import org.societies.groups.group.Group;
import org.societies.groups.member.Member;

/**
 * Represents a GroupPublisher
 */
public interface MemberGroupPublisher {

    ListenableFuture<Member> publishGroup(Member member, Group group);
}

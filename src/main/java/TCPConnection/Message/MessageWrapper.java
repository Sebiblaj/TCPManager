package Message;

import java.util.Objects;

public final class MessageWrapper {
    private final Message message;
    private final int lastIndex;

    public MessageWrapper(Message message, int lastIndex) {
        this.message = message;
        this.lastIndex = lastIndex;
    }

    public Message message() {
        return message;
    }

    public int lastIndex() {
        return lastIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MessageWrapper) obj;
        return Objects.equals(this.message, that.message) &&
                this.lastIndex == that.lastIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, lastIndex);
    }

    @Override
    public String toString() {
        return "MessageWrapper[" +
                "message=" + message + ", " +
                "lastIndex=" + lastIndex + ']';
    }


}

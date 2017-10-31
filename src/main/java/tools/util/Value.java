package tools.util;

/**
 * Represent a value that can be buffered/updated.
 * 
 * @author A. CANDIAN
 *
 * @param <T>
 *            The type of value.
 */
public final class Value<T> {
    /**
     * The buffered value of the {@link Value}.
     */
    private T bufferedValue;
    /**
     * The current value of the {@link Value}.
     */
    private T currentValue;
    /**
     * Flag indicating if the current value has been changed since the latest
     * update or revert.
     */
    private boolean modified;

    /**
     * Construct a new {@link Value} with an initial value.
     * 
     * @param value
     *            The initial value of the created {@link Value}.
     */
    public Value(T value) {
        this.currentValue = value;
        update();
    }

    /**
     * Put the current value in the buffer and change the modified flag to
     * false.
     */
    public void update() {
        bufferedValue = currentValue;
        modified = false;
    }

    /**
     * Replace the current value by the buffered one, and change the modified
     * flag to false.
     */
    public void revert() {
        currentValue = bufferedValue;
        modified = false;
    }

    /**
     * Return the current value.
     * 
     * @return The current value.
     */
    public T getCurrentValue() {
        return currentValue;
    }

    /**
     * Change the current value and change the modified flag to true.
     * 
     * @param value
     *            The current value.
     */
    public void setCurrentValue(T value) {
        this.currentValue = value;
        modified = true;
    }

    /**
     * Show if the value was modified.
     * 
     * @return <code>true</code> if setValue was called and not followed by a
     *         revert or update call.
     */
    public boolean isModified() {
        return modified;
    }
}

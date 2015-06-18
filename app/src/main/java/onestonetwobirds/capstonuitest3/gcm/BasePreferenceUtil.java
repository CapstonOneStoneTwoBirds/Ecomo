package onestonetwobirds.capstonuitest3.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BasePreferenceUtil
{
    private SharedPreferences _sharedPreferences;

    protected BasePreferenceUtil(Context $context)
    {
        super();
        _sharedPreferences = PreferenceManager.getDefaultSharedPreferences($context);
    }

    /**
     * key 역���� ����
     *
     * @param key
     *           Ű ��
     * @param value
     *           ����
     */
    protected void put(String $key, String $value)
    {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putString($key, $value);
        editor.commit();
    }

    /**
     * String �� ��������
     *
     * @param key
     *           Ű ��
     * @return String (�⺻�� null)
     */
    protected String get(String $key)
    {
        return _sharedPreferences.getString($key, null);
    }

    /**
     * key ����
     *
     * @param key
     *           Ű ��
     * @param value
     *           ����
     */
    protected void put(String $key, boolean $value)
    {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putBoolean($key, $value);
        editor.commit();
    }

    /**
     * Boolean �� ��������
     *
     * @param key
     *           Ű ��
     * @param defValue
     *           �⺻��
     * @return Boolean
     */
    protected boolean get(String $key, boolean $default)
    {
        return _sharedPreferences.getBoolean($key, $default);
    }

    /**
     * key ����
     *
     * @param key
     *           Ű ��
     * @param value
     *           ����
     */
    protected void put(String $key, int $value)
    {
        SharedPreferences.Editor editor = _sharedPreferences.edit();
        editor.putInt($key, $value);
        editor.commit();
    }

    /**
     * int �� ��������
     *
     * @param key
     *           Ű ��
     * @param defValue
     *           �⺻��
     * @return int
     */
    protected int get(String $key, int $default)
    {
        return _sharedPreferences.getInt($key, $default);
    }
}
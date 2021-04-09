package com.retrofit.app.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.retrofit.app.exception.InvalidInputException;
import io.micrometer.core.instrument.util.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

public abstract class EntityHelper {

  public static final String COMMA = ",";
  public static final String UNDERSCORE = "_";
  public static final String UID_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final Logger LOGGER = LoggerFactory.getLogger(EntityHelper.class);
  private static final String ERR_INVALID_ENTITY_ID = "invalid entity id: %s";
  private static final String NOT_AVAILABLE = "N/A";
  private static final String RANDOM_PO_PREFIX = "MMPO-";
  private static final String RANDOM_PO_BATCH_PREFIX = "MMB-";
  private static final String RANDOM_SHELF_REQUEST_PREFIX = "MMR-";

  private EntityHelper(){}

  public static <T> T getNotNull(T objectValue, T newValue) {
    return newValue != null ? newValue : objectValue;
  }

  public static String generateUuid() {
    UUID uuid = UUID.randomUUID();
    return uuid.toString();
  }

  public static String generatePOReferenceId() {
    return RANDOM_PO_PREFIX.concat(generateRandomAlphaString(6));
  }

  public static String generateShelfRequestReferenceId() {
    return RANDOM_SHELF_REQUEST_PREFIX.concat(generateRandomAlphaString(6));
  }

  public static String generatePOBatchNo() {
    return RANDOM_PO_BATCH_PREFIX.concat(generateRandomAlphaString(5));
  }

  public static <T> List<T> shrinkListSize(List<T> list, Integer shrinkSize) {
    if (EntityHelper.isEmpty(list)) {
      return Collections.emptyList();
    }
    if (list.size() < shrinkSize) {
      return list;
    }
    return list.subList(0, shrinkSize);
  }

  public static boolean isValidPhone(String country, String number) {
    if ("PK".equals(country))
      return Pattern.compile("^(\\+923)([0-9]{9})$").matcher(number).matches();

    return false;
  }

  public static int getCountInBusinessEntity(Query countQuery) {
    Long o = (Long) countQuery.getSingleResult();
    if (o > Integer.MAX_VALUE) {
      throw new ArithmeticException("Value cannot fit in an int: " + o);
    }
    return o.intValue();
  }

  public static String generateRandomString(int length) {
    byte[] bytes = new byte[length];
    new Random().nextBytes(bytes);
    return Base64.getEncoder().encodeToString(bytes);
  }

  public static String generateRandomAlphaString(int length) {
    if (length < 0) return "";

    String alphabeticalCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    StringBuilder builder = new StringBuilder();
    while (length-- != 0) {
      int character = new Random().nextInt(alphabeticalCharacters.length());
      builder.append(alphabeticalCharacters.charAt(character));
    }
    return builder.toString();
  }

  public static boolean isFirstLongInStringGreater(String first, String second) {
    long firstLong = Long.parseLong(first);
    long secondLong = Long.parseLong(second);
    return firstLong > secondLong;
  }

  public static boolean isFirstIntInStringGreater(String first, String second) {
    int firstInt = Integer.parseInt(first);
    int secondInt = Integer.parseInt(second);
    return firstInt > secondInt;
  }

  public static <T> List<T> makeNotNull(List<T> list) {
    return list != null ? list : new ArrayList<>();
  }

  public static void validateEntityId(int entityId) {
    if (entityId <= 0) {
      throw new IllegalArgumentException(String.format(ERR_INVALID_ENTITY_ID, entityId));
    }
  }

  public static boolean notNull(Object o) {
    return o != null;
  }

  public static boolean allNotNull(Object... o) {
    if (o == null) {
      return false;
    }
    for (Object o1 : o) {
      if (o1 == null) {
        return false;
      }
    }

    return true;
  }

  public static boolean anyNotNull(Object... o) {
    if (o == null) {
      return false;
    }
    for (Object o1 : o) {
      if (o1 != null) {
        return true;
      }
    }

    return false;
  }

  public static void validateEntityId(String entityId) {
    if ("".equals(entityId)) {
      throw new IllegalArgumentException(String.format(ERR_INVALID_ENTITY_ID, entityId));
    }
  }

  public static boolean notNullAndMoreZero(Integer value) {
    return (value != null) && (value > 0);
  }

  public static boolean isStringSet(String str) {
    return (str != null) && !str.isEmpty();
  }

  public static boolean isStringNotSet(String str) {
    return !isStringSet(str);
  }

  public static boolean doesStringContainOnlySpaces(String str) {
    String nonSpaceString = str.replace(" ", "");
    return nonSpaceString.length() == 0;
  }

  public static boolean isAnyNotSet(String... strs) {
    if (isNull(strs)) {
      return true;
    }
    for (String str : strs) {
      if (isStringNotSet(str)) {
        return true;
      }
    }
    return false;
  }

  public static String setDefaultStringIfEmptyOrNull(String str) {
    if ((str != null) && !str.isEmpty()) {
      return str;
    }
    return NOT_AVAILABLE;
  }

  public static Integer setDefaultIntegerIfEmptyOrNull(Integer val) {
    if (isIdSet(val)) {
      return val;
    }
    return -1;
  }

  public static boolean isIdSet(Integer... ids) {
    if (ids == null) {
      return false;
    }
    for (Integer id : ids) {
      if (!((id != null) && (id > 0))) {
        return false;
      }
    }

    return true;
  }

  public static String base64Encode(byte[] data) {
    return Base64.getEncoder().encodeToString(data);
  }

  public static String base64Decode(byte[] data) {
    return new String(Base64.getDecoder().decode(data));
  }

  public static boolean idIsNotSet(Integer... ids) {
    if (ids == null) {
      return false;
    }
    for (Integer id : ids) {
      if (isIdSet(id)) {
        return false;
      }
    }
    return true;
  }

  public static String getIdOrHyphen(Integer id) {
    return isIdSet(id) ? id.toString() : "-";
  }

  public static boolean isIdSet(String id) {
    return isStringSet(id);
  }

  public static boolean isSet(Boolean b) {
    return b != null && b.equals(Boolean.TRUE);
  }

  public static boolean isSet(String s) {
    return isIdSet(s);
  }

  public static boolean isSet(Integer i) {
    return isIdSet(i);
  }

  public static boolean isNull(Object o) {
    return o == null;
  }

  public static boolean isNotNull(Object o) {
    return !isNull(o);
  }

  public static boolean isNotNull(Object... o) {
    if (isNull(o)) return false;
    for (Object o1 : o) {
      if (o1 == null) {
        return false;
      }
    }
    return true;
  }

  public static boolean anyNull(Object... os) {
    for (Object o : os) {
      if (o == null) {
        return true;
      }
    }
    return false;
  }

  public static boolean allNull(Object... os) {
    for (Object o : os) {
      if (o != null) {
        return false;
      }
    }
    return true;
  }

  public static <T> Set<T> toSet(List<T> list) {
    return list != null ? new LinkedHashSet<>(list) : new LinkedHashSet<>();
  }

  public static <T> List<T> toList(Collection<T> set) {
    return set != null ? new ArrayList<>(set) : new ArrayList<>();
  }

  public static <T> List<T> safeList(List<T> list) {
    return list == null ? Collections.emptyList() : list;
  }

  public static <T> Integer indexOf(Set<T> set, T o) {
    Integer i = 0;
    for (T t : set) {
      if (t.equals(o)) {
        return i;
      }
      i++;
    }
    return 0;
  }

  public static <T> void setOnHashSet(Set<T> set, T o, Integer position) {
    List<T> ts = new ArrayList<>(set);
    ts.set(position, o);
    new HashSet<>(ts);
  }

  public static boolean isSingleElementCollection(List<String> tails) {
    if (tails == null) {
      return false;
    }
    return tails.size() == 1;
  }

  public static boolean isNotEmpty(List<?> list) {
    return list != null && !list.isEmpty();
  }

  public static boolean isEmpty(List<?> list) {
    return (!isNotEmpty(list));
  }

  public static Boolean isSetPopulated(Set<?> set) {
    return set != null && !set.isEmpty();
  }

  public static boolean isNotEmpty(Map<?, ?> map) {
    return map != null && !map.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> map) {
    return (!isNotEmpty(map));
  }

  public static Boolean removeNullElements(List<?> list) {
    if (list == null) {
      return Boolean.FALSE;
    }
    list.removeAll(Collections.singleton(null));
    return !list.isEmpty();
  }

  /*
   * Remove duplicate objects from the supplied list maintaining the order of
   * the elements
   */
  public static <T> List<T> removeDuplicates(List<T> list) {
    if (!CollectionUtils.isEmpty(list)) {
      Set<T> set = new LinkedHashSet<>(list);
      list.clear();
      list.addAll(set);
    }
    return list;
  }

  public static Boolean isPageNumAndPageSizeSet(Integer pageNum, Integer pageSize) {
    return pageNum != null && pageNum >= 0 && pageSize != null && pageSize > 0;
  }

  /*
   * Returns the full name in the format: firstName + middleName + lastName
   */
  public static String getPersonName(String firstName, String middleName, String lastName) {
    StringBuilder name = new StringBuilder();
    name.append(firstName);
    if (isSet(middleName)) {
      name.append(" ").append(middleName);
    }
    if (isSet(lastName)) {
      name.append(" ").append(lastName);
    }
    return name.toString();
  }

  /*
   * Returns the rounded double to the specified number of decimal places
   */
  public static double round(double value, int places) {
    if (places < 0) {
      throw new IllegalArgumentException();
    }

    BigDecimal bd = BigDecimal.valueOf(value);
    bd = bd.setScale(places, RoundingMode.HALF_UP);
    return bd.doubleValue();
  }

  public static String getDateOfMonthSuffix(final int day) {
    if (day < 1 || day > 31) {
      throw new IllegalArgumentException(String.format("Illegal day of month %d.", day));
    }

    if (day >= 11 && day <= 13) {
      return "th";
    }
    switch (day) {
      case 1:
        return "st";
      case 2:
        return "nd";
      case 3:
        return "rd";
      default:
        return "th";
    }
  }

  public static Integer toInteger(Object obj) {
    if (obj instanceof BigDecimal) {
      return ((BigDecimal) obj).intValue();
    }
    return null;
  }

  public static boolean isNotBlankOrNa(String str) {
    return StringUtils.isNotBlank(str) && !NOT_AVAILABLE.equalsIgnoreCase(str);
  }

  public static List<String> allStringsToLower(List<String> list) {
    if (isNull(list)) {
      return safeList(null);
    }

    for (int i = 0; i < list.size(); i++) {
      list.set(i, list.get(i).toLowerCase());
    }

    return list;
  }

  public static List<String> allStringsTrim(List<String> list) {
    if (isNull(list)) {
      return safeList(null);
    }

    for (int i = 0; i < list.size(); i++) {
      list.set(i, list.get(i).trim());
    }

    return list;
  }

  public static boolean isBitOn(Object object) {
    return (isNotNull(object) && object.equals(1));
  }

  public static <T> String convertModelToJSONString(T tModelClass) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(tModelClass);
    } catch (Exception ex) {
      LOGGER.error("Unable to parse Model to JSON", ex);
    }
    return null;
  }

  public static <T> T convertJSONStringToModel(String jsonInString, Class<T> tClass) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(jsonInString, tClass);
    } catch (Exception ex) {
      LOGGER.error("Unable to parse JSON to Model", ex);
    }
    return null;
  }

  public static String getDurationInHours(Integer duration) {
    return convertMinutesToHours(duration) + " hours";
  }

  public static Integer convertMinutesToHours(Integer mins) {
    if (isNotNull(mins) && mins != 0) {
      mins /= 60;
      return mins;
    }
    return 0;
  }

  /**
   * Supports till 20. Add more if needed
   *
   * @param number
   * @description This is number to ordinal converter
   * @return string
   */
  public static String numberToOrdinalWord(Integer number) {
    String[] ordinals = {
      "",
      "first",
      "second",
      "third",
      "fourth",
      "fifth",
      "sixth",
      "seventh",
      "eighth",
      "ninth",
      "tenth",
      "eleventh",
      "twelfth",
      "Thirteenth",
      "Fourteenth",
      "Fifteenth",
      "Sixteenth",
      "Seventeenth",
      "Eighteenth",
      "Nineteenth",
      "Twentieth",
    };

    return (number <= ordinals.length - 1) ? ordinals[number] : "";
  }

  public static List<String> convertMapToList(Map<String, String> map) {
    List<String> list = new ArrayList<>();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      list.add(entry.getKey());
      list.add(entry.getValue());
    }
    return list;
  }

  public static boolean isValueExistInToken(String tokenValue, String checkValue) {
    StringTokenizer stringTokenizer = new StringTokenizer(tokenValue, ",");
    boolean flag = false;
    while (stringTokenizer.hasMoreElements()) {
      if (checkValue.equals(stringTokenizer.nextElement())) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  public static List<Integer> splitCommaSeparatedStringToListOfInteger(String s) {
    List<Integer> ints = new ArrayList<>();
    if (isNull(s)) {
      return ints;
    }
    String[] split = s.split(COMMA);
    for (String st : split) {
      ints.add(Integer.valueOf(st));
    }
    return ints;
  }

  @SafeVarargs
  public static <T> T getNonNullValue(T... ts) {
    for (T t : ts) {
      if (null != t) {
        return t;
      }
    }
    return null;
  }

  public static boolean anyNotSet(String... s) {
    if (s == null) {
      return true;
    }
    for (String s1 : s) {
      if (isStringNotSet(s1)) {
        return true;
      }
    }

    return false;
  }

  public static boolean isNotSet(String s) {
    return isStringNotSet(s);
  }

  public static LocalDateTime currentDate() {
    return LocalDateTime.now();
  }

  public static boolean isTrue(Boolean value) {
    if (EntityHelper.isNotNull(value)) {
      return value;
    }
    return false;
  }

  /**
   * This method will join a list of strings into a sentence format. E.g [apple, banana, mango] will
   * return "apple, banana and mango"
   *
   * @param st list of string input
   * @return sentence format string
   */
  public static String joinWithCommaAndAnd(List<String> st) {
    StringBuilder toReturn = new StringBuilder();
    int count = 1;
    for (String s : st) {
      toReturn.append(s);
      if (st.size() != count) {
        toReturn.append((count == (st.size() - 1)) ? " and " : ", ");
      }
      count++;
    }

    return toReturn.toString();
  }

  public static String joingString(String... val) {
    StringBuilder builder = new StringBuilder();
    if (val != null && val.length > 0) {
      for (String s : val) {
        if (EntityHelper.isStringSet(s))
          builder.append(s);
      }
    }
    return builder.toString();
  }

  /**
   * selects random elements from collection
   *
   * @param lst collection to select items from
   * @param n number of items to select
   * @return list of randomly selected n elements
   */
  public static <T> List<T> pickNRandom(Collection<T> lst, int n) {
    List<T> copy = new ArrayList<>(lst);
    if (lst.size() <= n) return copy;
    Collections.shuffle(copy);
    return copy.subList(0, n);
  }

  public static boolean isValidEmailAddress(String email) {

    Pattern emailAddressRegex =
        Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
    Matcher matcher = emailAddressRegex.matcher(email);
    return matcher.find();
  }

  public static boolean isEmailNotValid(String email) {
    return !isValidEmailAddress(email);
  }

  public static boolean isDoubleSetAndNonZero(Double d) {
    return isNotNull(d) && !d.equals(0d);
  }

  public static boolean isNumberSetAndNonZero(Number d) {
    return isNotNull(d) && !d.equals(0);
  }

  public static boolean checkId(Number d) {
    return isNumberSetAndNonZero(d);
  }

  public static boolean isValidLong(String num) {
    try {
      Long.parseLong(num);
    } catch (NumberFormatException | NullPointerException e) {
      return false;
    }
    return true;
  }

  public static byte[] toByteArray(InputStream is) throws IOException {

    try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      byte[] b = new byte[4096];

      int n;
      while ((n = is.read(b)) != -1) {
        output.write(b, 0, n);
      }

      return output.toByteArray();
    }
  }

  public static double getDouble(String numberStr) {
    return getDouble(numberStr, true);
  }

  public static double getDouble(String numberStr, boolean throwException) {
    if (StringUtils.isNotBlank(numberStr)) {
      try {
        return Double.parseDouble(numberStr);
      } catch (NumberFormatException e) {
        LOGGER.error("Failed to parse {} into double", numberStr, e);
        if (throwException) {
          throw new InvalidInputException("Provided string cannot be converted to number");
        }
      }
    }
    return 0;
  }

  public static boolean isBlank(String resourceName) {
    return resourceName == null || resourceName.trim().isEmpty();
  }

  public static Integer stringToInteger(String obj) {
    if (obj != null) {
      return Integer.parseInt(obj);
    }
    return null;
  }

  public static double getMax(double val1, double val2) {
    if (val1 != 0 && val1 > val2) {
      return val2;
    }
    return val1;
  }

  public static boolean containsBlankString(List<String> list) {

    return list.stream().anyMatch(StringUtils::isBlank);
  }

  public static String createCsvFilePath(String fileSavePath, String fileName) {
    return fileSavePath + fileName + LocalDateTime.now() + ".csv";
  }
}

package br.com.staroski.games.spacearena.server.corba;

/**
* br/com/staroski/games/spacearena/server/corba/EnergyItemProviderHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./br/com/staroski/games/spacearena/server/corba/interfaces.idl
* Ter�a-feira, 3 de Dezembro de 2013 19h26min51s BRST
*/

public final class EnergyItemProviderHolder implements org.omg.CORBA.portable.Streamable
{
  public br.com.staroski.games.spacearena.server.corba.EnergyItemProvider value = null;

  public EnergyItemProviderHolder ()
  {
  }

  public EnergyItemProviderHolder (br.com.staroski.games.spacearena.server.corba.EnergyItemProvider initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = br.com.staroski.games.spacearena.server.corba.EnergyItemProviderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    br.com.staroski.games.spacearena.server.corba.EnergyItemProviderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return br.com.staroski.games.spacearena.server.corba.EnergyItemProviderHelper.type ();
  }

}
